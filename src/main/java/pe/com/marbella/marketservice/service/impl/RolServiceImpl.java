package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.marbella.marketservice.dto.RolDTO;
import pe.com.marbella.marketservice.model.Rol;
import pe.com.marbella.marketservice.repository.RolRepository;
import pe.com.marbella.marketservice.service.RolService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz RolService.
 * Proporciona métodos para gestionar los roles.
 */
@Service
public class RolServiceImpl implements RolService {
    @Autowired
    RolRepository rolRepository;

    /**
     * Mapea un objeto Rol a un objeto RolDTO.
     *
     * @param rol El objeto Rol a mapear.
     * @return El objeto RolDTO mapeado.
     */
    private RolDTO mapToDTO(Rol rol){
        return new RolDTO(
                rol.getIdRol(),
                rol.getNombreRol().name());
    }

    /**
     * Obtiene una lista de todos los roles activos.
     *
     * @return Una lista de objetos RolDTO que representan los roles activos.
     * @throws Exception Si ocurre un error al obtener los roles.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RolDTO> listadoRoles() throws Exception {
        return rolRepository.findByEstado(true).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un rol por su ID.
     *
     * @param id El ID del rol.
     * @return El objeto RolDTO encontrado.
     * @throws Exception Si ocurre un error al obtener el rol o si no se encuentra un rol con el ID proporcionado.
     */
    @Override
    @Transactional(readOnly = true)
    public RolDTO buscarRol(Long id) throws Exception {
        Rol rol = rolRepository.findByIdRolAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
        return mapToDTO(rol);
    }
}
