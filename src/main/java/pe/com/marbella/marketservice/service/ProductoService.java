package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.model.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> findAll() throws Exception;
    Producto findById(Long id) throws Exception;
    Producto save(Producto marca) throws Exception;
    Producto update(Long id, Producto marca) throws Exception;
    boolean delete(Long id) throws Exception;
}
