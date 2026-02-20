package lpj.controlproductos.services.interfaces;

import lpj.controlproductos.model.Usuario;
import lpj.controlproductos.repositories.UsuarioRepository;

import java.util.List;

public interface UsuarioService {

    public List<Usuario> getUsuarios();

    public Usuario getUsuarioById(Long idUsuario);

    public Usuario saveUsuario(Usuario usuario);

    public void deleteUsuario(Usuario usuario);

    public boolean existePorUsername(String username);

    public Usuario findByUsername(String username);

    public boolean esUltimoAdmin(Usuario usuario);
}


