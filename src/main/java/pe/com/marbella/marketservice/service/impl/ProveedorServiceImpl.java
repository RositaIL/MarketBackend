package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.dto.ProveedorDTO;
import pe.com.marbella.marketservice.model.Proveedor;
import pe.com.marbella.marketservice.repository.ProveedorRepository;
import pe.com.marbella.marketservice.service.ProveedorService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProveedorServiceImpl implements ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

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

    private Proveedor mapToEntity(ProveedorDTO proveedorDTO) {
        return new Proveedor(proveedorDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorDTO> findAll() throws Exception {
        return proveedorRepository.findByEstado(true).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProveedorDTO findById(Long id) throws Exception {
        Proveedor proveedor = proveedorRepository.findByIdProveedorAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));
        return mapToDTO(proveedor);
    }

    @Override
    @Transactional
    public ProveedorDTO save(ProveedorDTO proveedorDTO) throws Exception {
        Proveedor newProveedor = mapToEntity(proveedorDTO);
        Proveedor respuesta = proveedorRepository.save(newProveedor);
        return mapToDTO(respuesta);
    }

    @Override
    @Transactional
    public ProveedorDTO update(ProveedorDTO proveedorDTO) throws Exception {
        Proveedor proveedor = proveedorRepository.findById(proveedorDTO.idProveedor())
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));

        proveedor.setNombreProv(proveedorDTO.nombreProv());
        proveedor.setDireccProv(proveedorDTO.direccProv());
        proveedor.setTelefProv(proveedorDTO.telefProv());
        proveedor.setRucProv(proveedorDTO.rucProv());
        proveedor.setEmailProv(proveedorDTO.emailProv());
        proveedor.setNomRepresentante(proveedor.getNomRepresentante());

        return mapToDTO(proveedorRepository.save(proveedor));
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        Proveedor proveedor = proveedorRepository.findByIdProveedorAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));
        proveedor.eliminar();
        proveedorRepository.save(proveedor);
    }
}
