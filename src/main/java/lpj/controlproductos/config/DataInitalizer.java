package lpj.controlproductos.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lpj.controlproductos.model.Rol;
import lpj.controlproductos.model.Usuario;
import lpj.controlproductos.services.interfaces.RolService;
import lpj.controlproductos.services.interfaces.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitalizer implements CommandLineRunner {

    private final RolService rolService;

    private final UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {

        crearRolSiNoExiste("ROLE_ADMIN");
        crearRolSiNoExiste("ROLE_USER");

        crearPrimerAdmin();

        log.info("Roles verificados");

    }

    private void crearRolSiNoExiste(String nombreRol){
        if (rolService.findRolByNombre(nombreRol) == null){
            Rol admin = new Rol();
            admin.setNombre(nombreRol);
            rolService.saveRol(admin);
        }

    }

    private void crearPrimerAdmin(){
        if (usuarioService.getUsuarios().isEmpty()){
            Usuario primerUsuario = new Usuario();
            primerUsuario.setUsername("admin");
            primerUsuario.setPassword("1234");
            primerUsuario.getRoles().add(rolService.findRolByNombre("ROLE_ADMIN"));
            log.info("Se ha creado el primer administrador: username:"+primerUsuario.getUsername()+" | password: 1234");
            usuarioService.saveUsuario(primerUsuario);
        }
    }

}
