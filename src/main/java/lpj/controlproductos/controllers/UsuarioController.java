package lpj.controlproductos.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lpj.controlproductos.model.Usuario;
import lpj.controlproductos.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UsuarioController {


    private final UsuarioService usuarioService;

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


}
