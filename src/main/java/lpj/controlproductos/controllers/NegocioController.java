package lpj.controlproductos.controllers;

import lombok.extern.slf4j.Slf4j;
import lpj.controlproductos.model.Negocio;
import lpj.controlproductos.model.Usuario;
import lpj.controlproductos.services.interfaces.NegocioService;
import lpj.controlproductos.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class NegocioController {

    @Autowired
    NegocioService negocioService;


    @PostMapping("/admin/negocio/guardar")
    public String guardar( Negocio negocio){
        negocioService.saveNegocio(negocio);
        return "redirect:/";
    }

    @GetMapping("/admin/negocio/agregar")
    public String agregar(Model model){
        model.addAttribute("negocio",new Negocio());
        return "negocio/agregarNegocio";
    }

    @PostMapping("/admin/negocio/eliminar/{idNegocio}")
    public String eliminar(@PathVariable Long idNegocio){
        log.info("Se ha eliminado el negocio: "+negocioService.getNegocioById(idNegocio).getNombreNegocio()+" con id "+idNegocio);
        negocioService.deleteNegocio(negocioService.getNegocioById(idNegocio));
        return "redirect:/";
    }

    @GetMapping("/admin/negocio/editar/{idNegocio}")
    public String editar(@PathVariable Long idNegocio, Model model){
        model.addAttribute("negocio",negocioService.getNegocioById(idNegocio));
        return "negocio/editarNegocio";
    }
}
