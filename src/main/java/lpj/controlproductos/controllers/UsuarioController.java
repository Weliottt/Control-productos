package lpj.controlproductos.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lpj.controlproductos.model.Rol;
import lpj.controlproductos.model.Usuario;
import lpj.controlproductos.services.interfaces.RolService;
import lpj.controlproductos.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UsuarioController {


    private final UsuarioService usuarioService;

    private final RolService rolService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/admin/usuarios")
    public String listaUsuarios(Model model){
        List<Usuario> usuarios = usuarioService.getUsuarios();
        model.addAttribute("usuarios",usuarios);
        return "usuario/listaUsuarios";
    }

    @PostMapping("/admin/usuario/eliminar/{idUsuario}")
    public String eliminarUsuario(@PathVariable Long idUsuario){
        Usuario usuarioEliminado = usuarioService.getUsuarioById(idUsuario);
        log.info("Se ha eliminado el usuario: "+usuarioEliminado.getUsername()+" con id "+usuarioEliminado.getIdUsuario());
        usuarioService.deleteUsuario(usuarioEliminado);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/admin/usuario/editar/{idUsuario}")
    public String editarUsuario(@PathVariable Long idUsuario, Model model){


        Usuario usuario = usuarioService.getUsuarioById(idUsuario);
        List<Rol> roles =rolService.getRoles();
        model.addAttribute("usuario",usuario);
        model.addAttribute("roles",roles);
        return "usuario/editarUsuario";
    }

    @PostMapping("/admin/usuario/guardar")
    public String guardarUsuario(Usuario usuarioform){

        Usuario usuarioDB = usuarioService.getUsuarioById(usuarioform.getIdUsuario());

        usuarioDB.setUsername(usuarioform.getUsername());
        usuarioDB.setRoles(usuarioform.getRoles());

        if(usuarioform.getPassword() != null && !usuarioform.getPassword().isBlank()){
            usuarioDB.setPassword(passwordEncoder.encode(usuarioform.getPassword()));
            log.info("Se ha cambiado la contraseña del usuario con id:" +usuarioDB.getIdUsuario());
        }

        log.info("Se ha actualizado el usuario con id: "+usuarioDB.getIdUsuario());
        usuarioService.saveUsuario(usuarioDB);
        return "redirect:/admin/usuarios";
    }

}
