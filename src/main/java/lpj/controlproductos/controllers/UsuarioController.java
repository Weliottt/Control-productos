package lpj.controlproductos.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lpj.controlproductos.model.Rol;
import lpj.controlproductos.model.Usuario;
import lpj.controlproductos.services.interfaces.RolService;
import lpj.controlproductos.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UsuarioController {


    private final UsuarioService usuarioService;

    private final RolService rolService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/admin/usuarios")
    public String listaUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "usuario/listaUsuarios";
    }

    @PostMapping("/admin/usuario/eliminar/{idUsuario}")
    public String eliminarUsuario(@PathVariable Long idUsuario, RedirectAttributes redirectAttributes) {
        Usuario usuarioEliminado = usuarioService.getUsuarioById(idUsuario);

        //Verificamos que el usuario a eliminar no sea el ultimo admin disponible
        if (usuarioService.esUltimoAdmin(usuarioEliminado)) {
            redirectAttributes.addFlashAttribute("noHayAdmin", true);
            return "redirect:/admin/usuarios";
        }

        //Verificamos que el usuario a eliminar no sea el mismo que esta con la sesion activa
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (usuarioEliminado.getUsername().equals(auth.getName())) {
            log.info("se ha editado el mismo usuario de la sesion");
            redirectAttributes.addFlashAttribute("mismoUsuario", true);
            return "redirect:/admin/usuarios";
        }

        log.info("Se ha eliminado el usuario: " + usuarioEliminado.getUsername() + " con id " + usuarioEliminado.getIdUsuario());
        usuarioService.deleteUsuario(usuarioEliminado);
        return "redirect:/admin/usuarios";

    }

    @GetMapping("/admin/usuario/editar/{idUsuario}")
    public String editarUsuario(@PathVariable Long idUsuario, Model model) {


        Usuario usuario = usuarioService.getUsuarioById(idUsuario);
        List<Rol> roles = rolService.getRoles();
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", roles);
        return "usuario/editarUsuario";
    }

    @PostMapping("/admin/usuario/guardar")
    public String guardarUsuario(Usuario usuarioform, RedirectAttributes redirectAttributes, Authentication authentication) {

        //Obtenemos el usuario guardado en la BD
        Usuario usuarioDB = usuarioService.getUsuarioById(usuarioform.getIdUsuario());

        //Verificamos que el usuario de la BD sea el último admin para evitar quitarle el rol
        if (usuarioService.esUltimoAdmin(usuarioDB) &&
                usuarioform.getRoles().stream().anyMatch(r -> "ROLE_USER".equals(r.getNombre()))) {

            redirectAttributes.addFlashAttribute("noHayAdmin", true);
            log.info("No se pudo editar el usuario: " + usuarioDB.getUsername() + " porque es el último admin");
            return "redirect:/admin/usuarios";
        }

        //Obtenemos el nombre de usuario que está logueado al momento de hacer el guardado
        String usernameLogueado = authentication.getName();
        boolean esMismoUsuario = usernameLogueado.equals(usuarioform.getUsername());

        //Reemplazamos los nuevos datos que ingresó el usuario directamente sobre el usuario de la BD
        usuarioDB.setUsername(usuarioform.getUsername());
        usuarioDB.setRoles(usuarioform.getRoles());

        //Verificamos si el usuario cambió la contraseña
        if (usuarioform.getPassword() != null && !usuarioform.getPassword().isBlank()) {
            usuarioDB.setPassword(passwordEncoder.encode(usuarioform.getPassword()));
            log.info("Se ha cambiado la contraseña del usuario con id:" + usuarioDB.getIdUsuario());
        }

        log.info("Se ha actualizado el usuario con id: " + usuarioDB.getIdUsuario());
        //Guardamos el nuevo usuario en la BD
        usuarioService.saveUsuario(usuarioDB);

        //Si el usuario se editó a sí mismo se lo manda a iniciar sesión de nuevo para aplicar los cambios
        if (esMismoUsuario){
            SecurityContextHolder.clearContext();
            redirectAttributes.addFlashAttribute("usuarioEditado",true);
            return "redirect:/login";
        }

            return "redirect:/admin/usuarios";
    }


}
