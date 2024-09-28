package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.model.Medida;

import java.util.List;

public interface MedidaService {
    List<Medida> findAll() throws Exception;
    Medida findById(Long id) throws Exception;
    Medida save(Medida marca) throws Exception;
    Medida update(Medida marca) throws Exception;
    boolean delete(Long id) throws Exception;
}
