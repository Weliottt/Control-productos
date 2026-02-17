package lpj.controlproductos.services.interfaces;

import lpj.controlproductos.model.Rol;

import java.util.List;

public interface RolService {

    public List<Rol> getRoles();

    public Rol findRolByNombre(String nombreRol);

    public Rol getRolById(Long idRol);

    public Rol saveRol(Rol rol);

    public void deleteRol(Rol rol);

}
