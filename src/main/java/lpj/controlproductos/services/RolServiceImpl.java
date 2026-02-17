package lpj.controlproductos.services;

import lombok.RequiredArgsConstructor;
import lpj.controlproductos.model.Rol;
import lpj.controlproductos.repositories.RolRepository;
import lpj.controlproductos.services.interfaces.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    @Override
    public List<Rol> getRoles() {
        return rolRepository.findAll();
    }

    @Override
    public Rol findRolByNombre(String nombreRol) {return rolRepository.findRolByNombre(nombreRol);}

    @Override
    public Rol getRolById(Long idRol) {
        return rolRepository.findById(idRol).orElse(null);
    }

    @Override
    @Transactional
    public Rol saveRol(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    @Transactional
    public void deleteRol(Rol rol) {
        rolRepository.delete(rol);
    }
}
