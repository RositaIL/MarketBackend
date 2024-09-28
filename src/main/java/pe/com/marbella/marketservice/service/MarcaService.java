package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.model.Marca;

import java.util.List;

public interface MarcaService {

    List<Marca> findAll() throws Exception;
    Marca findById(Long id) throws Exception;
    Marca save(Marca marca) throws Exception;
    Marca update(Marca marca) throws Exception;
    boolean delete(Long id) throws Exception;
}
