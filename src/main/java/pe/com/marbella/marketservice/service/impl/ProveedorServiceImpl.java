package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.dto.ProveedorDTO;
import pe.com.marbella.marketservice.model.Proveedor;
import pe.com.marbella.marketservice.repository.ProveedorRepository;
import pe.com.marbella.marketservice.service.ProveedorService;

import java.util.Optional;

/**
 * Implementación de la interfaz ProveedorService.
 * Proporciona métodos para gestionar los proveedores.
 */
@Service
public class ProveedorServiceImpl implements ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

    /**
     * Mapea un objeto Proveedor a un objeto ProveedorDTO.
     *
     * @param proveedor El objeto Proveedor a mapear.
     * @return El objeto ProveedorDTO mapeado.
     */
    private ProveedorDTO mapToDTO(Proveedor proveedor) {
        return new ProveedorDTO(
                proveedor.getIdProveedor(),
                proveedor.getNombreProv(),
                proveedor.getDireccProv(),
                proveedor.getTelefProv(),
                proveedor.getRucProv(),
                proveedor.getEmailProv(),
                proveedor.getNomRepresentante()
        );
    }

    /**
     * Mapea un objeto ProveedorDTO a un objeto Proveedor.
     *
     * @param proveedorDTO El objeto ProveedorDTO a mapear.
     * @return El objeto Proveedor mapeado.
     */
    private Proveedor mapToEntity(ProveedorDTO proveedorDTO) {
        return new Proveedor(proveedorDTO);
    }

    /**
     * Obtiene una página de proveedores que coinciden con el nombre proporcionado.
     *
     * @param nombre   El nombre a buscar.
     * @param pageable Objeto Pageable para la paginación.
     * @return Una página de objetos ProveedorDTO.
     * @throws Exception Si ocurre un error al obtener los proveedores.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProveedorDTO> findAll(String nombre, Pageable pageable) throws Exception {
        return proveedorRepository.findByNombreContainingAndEstado(nombre, true,pageable)
                .map(this::mapToDTO);
    }

    /**
     * Obtiene un proveedor por su ID.
     *
     * @param id El ID del proveedor.
     * @return El objeto ProveedorDTO encontrado.
     * @throws Exception Si ocurre un error al obtener el proveedor.
     */
    @Override
    @Transactional(readOnly = true)
    public ProveedorDTO findById(Long id) throws Exception {
        Proveedor proveedor = proveedorRepository.findByIdProveedorAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));
        return mapToDTO(proveedor);
    }

    /**
     * Guarda un nuevo proveedor o actualiza uno existente si se encuentra inactivo con el mismo RUC.
     *
     * @param proveedorDTO El DTO del proveedor a guardar o actualizar.
     * @return El DTO del proveedor guardado o actualizado.
     * @throws Exception Si ocurre un error al guardar o actualizar el proveedor.
     */
    @Override
    @Transactional
    public ProveedorDTO save(ProveedorDTO proveedorDTO) throws Exception {
        Optional<Proveedor> inactiveProveedor = proveedorRepository.findProveedorByRucProvAndEstado(proveedorDTO.rucProv(), false);

        if (inactiveProveedor.isPresent()) {
            Proveedor proveedor = inactiveProveedor.get();
            proveedor.setEstado(true);
            Proveedor updatedProveedor = proveedorRepository.save(proveedor);
            return mapToDTO(updatedProveedor);
        }

        Proveedor newProveedor = mapToEntity(proveedorDTO);
        Proveedor respuesta = proveedorRepository.save(newProveedor);
        return mapToDTO(respuesta);
    }

    /**
     * Actualiza un proveedor existente.
     *
     * @param proveedorDTO El DTO del proveedor con la información actualizada.
     * @return El DTO del proveedor actualizado.
     * @throws Exception Si ocurre un error al actualizar el proveedor.
     */
    @Override
    @Transactional
    public ProveedorDTO update(ProveedorDTO proveedorDTO) throws Exception {
        Proveedor proveedor = proveedorRepository.findById(proveedorDTO.idProveedor())
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));

        if (proveedorDTO.nombreProv() != null && !proveedorDTO.nombreProv().trim().isEmpty()) {
            proveedor.setNombreProv(proveedorDTO.nombreProv());
        }
        if (proveedorDTO.direccProv() != null && !proveedorDTO.direccProv().trim().isEmpty()) {
            proveedor.setDireccProv(proveedorDTO.direccProv());
        }
        if (proveedorDTO.telefProv() != null && !proveedorDTO.telefProv().trim().isEmpty()) {
            proveedor.setTelefProv(proveedorDTO.telefProv());
        }
        if (proveedorDTO.rucProv() != null && !proveedorDTO.rucProv().trim().isEmpty()) {
            proveedor.setRucProv(proveedorDTO.rucProv());
        }
        if (proveedorDTO.emailProv() != null && !proveedorDTO.emailProv().trim().isEmpty()) {
            proveedor.setEmailProv(proveedorDTO.emailProv());
        }
        if (proveedorDTO.nomRepresentante() != null && !proveedorDTO.nomRepresentante().trim().isEmpty()) {
            proveedor.setNomRepresentante(proveedorDTO.nomRepresentante());
        }

        return mapToDTO(proveedorRepository.save(proveedor));
    }

    /**
     * Elimina un proveedor.
     *
     * @param id El ID del proveedor a eliminar.
     * @throws Exception Si ocurre un error al eliminar el proveedor.
     */
    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        Proveedor proveedor = proveedorRepository.findByIdProveedorAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));
        proveedor.eliminar();
        proveedorRepository.save(proveedor);
    }
}
