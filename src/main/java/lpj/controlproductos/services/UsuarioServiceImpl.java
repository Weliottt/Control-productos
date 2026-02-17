package lpj.controlproductos.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lpj.controlproductos.model.Rol;
import lpj.controlproductos.model.Usuario;
import lpj.controlproductos.repositories.UsuarioRepository;
import lpj.controlproductos.services.interfaces.RolService;
import lpj.controlproductos.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    private final RolService rolService;

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getUsuarioById(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElse(null);
    }

    @Override
    @Transactional
    public Usuario saveUsuario(Usuario usuario) {
        //Codificamos la contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        //Registramos la fecha de creacion del usuario
        usuario.setFechaAlta(LocalDate.now());

        //Le asignamos el rol de User
        Rol rolUsuario = rolService.findRolByNombre("ROLE_USER");

        if(rolUsuario == null){
            throw new RuntimeException("No se encontró el ROLE_USER en la base de datos");
        }

        usuario.getRoles().add(rolUsuario);

        log.info("Se ha creado un nuevo usuario: " + usuario.getUsername());

        //Lo guardamos en la base de datos
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void deleteUsuario(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //Buscamos el usuario
        Usuario usuario = usuarioRepository.findByUsername(username);

        //Si no se encuentra lanza una exception
        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }

        Set<GrantedAuthority> roles = new HashSet<>();

        //Recorremos la lista de roles del usuario
        for (Rol rol : usuario.getRoles()) {
            //Lo parseamos a SimpleGrantedAuthority para que Spring Security lo reconozca como rol
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }

        //Retornamos el usuario completo(nombre,contraseña codificada y roles)
        return new User(usuario.getUsername()
                , usuario.getPassword(),
                roles);
    }

    public boolean existePorUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }


}
