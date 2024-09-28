package pe.com.marbella.marketservice.service;

import org.springframework.transaction.annotation.Transactional;
import pe.com.marbella.marketservice.dto.EntradaDTO;
import pe.com.marbella.marketservice.model.Entrada;

import java.util.List;

public interface EntradaService {
    List<Entrada> findAll() throws Exception;
    Entrada findById(Long id) throws Exception;
    Entrada save(EntradaDTO entity) throws Exception;
    void delete(Long idEntrada) throws Exception;
}
