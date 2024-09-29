package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.dto.RolDTO;
import pe.com.marbella.marketservice.model.Rol;
import pe.com.marbella.marketservice.repository.RolRepository;
import pe.com.marbella.marketservice.service.RolService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {
    @Autowired
    RolRepository rolRepository;

    private RolDTO mapToDTO(Rol rol){
        return new RolDTO(
                rol.getIdRol(),
                rol.getNombreRol().name());
    }

    @Override
    public List<RolDTO> listadoRoles() throws Exception {
        return rolRepository.findByEstado(true).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RolDTO buscarRol(Long id) throws Exception {
        Rol rol = rolRepository.findByIdRolAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
        return mapToDTO(rol);
    }
}
