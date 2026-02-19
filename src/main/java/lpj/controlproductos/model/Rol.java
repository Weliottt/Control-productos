package lpj.controlproductos.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "rol")
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idRol;

    private String nombre;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private Set<Usuario> usuarios;

    public String getRol(){
        if("ROLE_USER".equals(this.nombre)){
            return "User";
        }else if("ROLE_ADMIN".equals(this.nombre)){
            return "Admin";
        }else
            return this.nombre;
    }

}

