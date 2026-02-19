package lpj.controlproductos.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lpj.controlproductos.model.Rol;
import lpj.controlproductos.services.interfaces.RolService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitalizer implements CommandLineRunner {

    private final RolService rolService;

    @Override
    public void run(String... args) throws Exception {

        if (rolService.findRolByNombre("ROLE_ADMIN") == null){
            Rol admin = new Rol();
            admin.setNombre("ROLE_ADMIN");
            rolService.saveRol(admin);
        }

        if (rolService.findRolByNombre("ROLE_USER") == null){
            Rol user = new Rol();
            user.setNombre("ROLE_USER");
            rolService.saveRol(user);
        }

        log.info("Roles verificados");

    }



}
