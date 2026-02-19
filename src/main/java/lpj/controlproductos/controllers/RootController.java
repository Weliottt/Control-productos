package lpj.controlproductos.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lpj.controlproductos.model.Negocio;
import lpj.controlproductos.model.Usuario;
import lpj.controlproductos.repositories.UsuarioRepository;
import lpj.controlproductos.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class RootController {

    @Autowired
    NegocioService negocioService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/")
    public String inicio(Model model) {

        List<Negocio> negocios = negocioService.listarNegocios();
        boolean vacio = negocios.isEmpty();

        model.addAttribute("negocios",negocios);
        model.addAttribute("vacio",vacio);

        return "layout/index";
    }

    @GetMapping("/login")
    public String login(){
        return "login/login";
    }

    @GetMapping("/register")
    public String mostrarForm(Model model){
        model.addAttribute("usuario",new Usuario());
        return "login/register";
    }

    @PostMapping("/register")
    public String guardarUsuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes redirectAttributes){

        if(usuarioService.existePorUsername(usuario.getUsername())){
            result.rejectValue("username","error.usuario","El nombre de usuario ya existe");
        }
        if (result.hasErrors()){
            return "login/register";
        }

        redirectAttributes.addFlashAttribute("success","Felicidades se ha creado un nuevo usuario. Por favor inicie sesión");
        log.info("Se ha creado un nuevo usuario");
        usuarioService.saveUsuario(usuario);
        return "redirect:/login";
    }

}
