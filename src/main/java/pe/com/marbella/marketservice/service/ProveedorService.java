package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.dto.ProveedorDTO;
import pe.com.marbella.marketservice.model.Proveedor;

import java.util.List;

public interface ProveedorService {
    List<ProveedorDTO> findAll() throws Exception;
    ProveedorDTO findById(Long id) throws Exception;
    ProveedorDTO save(ProveedorDTO proveedor) throws Exception;
    ProveedorDTO update(ProveedorDTO proveedor) throws Exception;
    void delete(Long id) throws Exception;
}
