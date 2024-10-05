package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.dto.ProveedorDTO;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface ProveedorService {
    List<ProveedorDTO> findAll(Pageable pageable) throws Exception;
    ProveedorDTO findById(Long id) throws Exception;
    ProveedorDTO save(ProveedorDTO proveedor) throws Exception;
    ProveedorDTO update(ProveedorDTO proveedor) throws Exception;
    void delete(Long id) throws Exception;
}
