package lpj.controlproductos.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "negocio")
public class Negocio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNegocio;

    private String nombreNegocio;

    private String descripcionNegocio;

    @OneToMany(mappedBy = "negocio")
    private List<Producto> productos;

}
