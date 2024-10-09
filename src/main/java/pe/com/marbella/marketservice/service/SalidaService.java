package pe.com.marbella.marketservice.service;

import org.springframework.data.domain.Pageable;
import pe.com.marbella.marketservice.dto.SalidaDTO;

import java.util.List;

public interface SalidaService {
    List<SalidaDTO> findAll(Pageable pageable) throws Exception;
    SalidaDTO findById(Long id) throws Exception;
    SalidaDTO save(SalidaDTO entity) throws Exception;
    void delete(Long idSalida) throws Exception;
}
