package pe.com.marbella.marketservice.service;

import org.springframework.data.domain.Page;
import pe.com.marbella.marketservice.dto.ProveedorDTO;

import org.springframework.data.domain.Pageable;

public interface ProveedorService {
    Page<ProveedorDTO> findAll(Pageable pageable) throws Exception;
    ProveedorDTO findById(Long id) throws Exception;
    ProveedorDTO save(ProveedorDTO proveedor) throws Exception;
    ProveedorDTO update(ProveedorDTO proveedor) throws Exception;
    void delete(Long id) throws Exception;
}
