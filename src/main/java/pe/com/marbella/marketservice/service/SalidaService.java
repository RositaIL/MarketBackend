package pe.com.marbella.marketservice.service;

import org.springframework.transaction.annotation.Transactional;
import pe.com.marbella.marketservice.dto.SalidaDTO;
import pe.com.marbella.marketservice.model.Salida;

import java.util.List;

public interface SalidaService {
    List<Salida> findAll() throws Exception;
    Salida findById(Long id) throws Exception;
    Salida save(SalidaDTO entity) throws Exception;
    void delete(Long idSalida) throws Exception;
}
