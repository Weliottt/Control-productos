package lpj.controlproductos.controllers;

import lombok.extern.slf4j.Slf4j;
import lpj.controlproductos.model.Categoria;
import lpj.controlproductos.model.Marca;
import lpj.controlproductos.model.Negocio;
import lpj.controlproductos.model.Producto;
import lpj.controlproductos.services.interfaces.CategoriaService;
import lpj.controlproductos.services.interfaces.MarcaService;
import lpj.controlproductos.services.interfaces.NegocioService;
import lpj.controlproductos.services.interfaces.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@Slf4j
public class ProductoController {

    @Autowired
    NegocioService negocioService;

    @Autowired
    CategoriaService categoriaService;

    @Autowired
    MarcaService marcaService;

    @Autowired
    ProductoService productoService;

    @GetMapping("/admin/producto/agregar/{idNegocio}")
    public String agregarProducto(@PathVariable Long idNegocio, Model model, Producto producto) {

        List<Categoria> categorias = categoriaService.getCategorias();
        List<Marca> marcas = marcaService.getMarcas();

        model.addAttribute("idNegocio", idNegocio);
        model.addAttribute("categorias", categorias);
        model.addAttribute("producto", producto);
        model.addAttribute("marcas", marcas);


        return "producto/agregarProducto";
    }

    @GetMapping("/user/negocio/{idNegocio}")
    public String listarProductos(@PathVariable Long idNegocio, Model model) {

        Negocio negocio = negocioService.getNegocioById(idNegocio);

        List<Producto> productos = negocio.getProductos();
        BigDecimal totalGeneral = BigDecimal.ZERO;

        for (Producto producto : productos){
            totalGeneral = totalGeneral.add(producto.getTotal());
        }

        model.addAttribute("productos", productos);
        model.addAttribute("idNegocio", idNegocio);
        model.addAttribute("totalGeneral",totalGeneral);

        return "producto/listaProductos";
    }

    @PostMapping("/admin/producto/guardar")
    public String guardar(@RequestParam Long idNegocio,
                          @RequestParam Long idMarca,
                          @RequestParam Long idCategoria,
                          Producto producto) {

        if (producto.getIdProducto() == null) {
            log.info("Se ha creado un nuevo producto");
        } else {
            log.info("Se ha editado el producto con id: " + producto.getIdProducto());
        }

        Negocio negocio = negocioService.getNegocioById(idNegocio);
        Categoria categoria = categoriaService.getCategoriaById(idCategoria);
        Marca marca = marcaService.getMarcaById(idMarca);

        producto.setNegocio(negocio);
        producto.setCategoriaProducto(categoria);
        producto.setMarcaProducto(marca);

        productoService.saveProducto(producto);

        return "redirect:/user/negocio/" + producto.getNegocio().getIdNegocio();
    }


    @GetMapping("/admin/producto/editar/{idProducto}")
    public String editar(@PathVariable Long idProducto, Model model) {

        Producto producto = productoService.getProductoById(idProducto);
        List<Marca> marcas = marcaService.getMarcas();
        List<Categoria> categorias = categoriaService.getCategorias();

        model.addAttribute("idNegocio", producto.getNegocio().getIdNegocio());
        model.addAttribute("producto", producto);
        model.addAttribute("marcas", marcas);
        model.addAttribute("categorias", categorias);

        return "producto/editarProducto";
    }

    @PostMapping("/admin/producto/eliminar/{idProducto}")
    public String eliminar(@PathVariable Long idProducto) {
        Long idNegocio = productoService.getProductoById(idProducto).getNegocio().getIdNegocio();
        log.info("Se ha borrado el producto: "+productoService.getProductoById(idProducto).getNombreProducto());
        productoService.deleteProducto(productoService.getProductoById(idProducto));
        return "redirect:/user/negocio/" + idNegocio;
    }
}
