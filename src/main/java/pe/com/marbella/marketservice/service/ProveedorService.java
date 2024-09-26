package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.model.Proveedor;

import java.util.List;

public interface ProveedorService {
    List<Proveedor> findAll() throws Exception;
    Proveedor findById(Long id) throws Exception;
    Proveedor save(Proveedor proveedor) throws Exception;
    Proveedor update(Long id, Proveedor proveedor) throws Exception;
    boolean delete(Long id) throws Exception;
}
