package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.dto.SalidaDTO;

import java.util.List;

public interface SalidaService {
    List<SalidaDTO> findAll() throws Exception;
    SalidaDTO findById(Long id) throws Exception;
    SalidaDTO save(SalidaDTO entity) throws Exception;
    void delete(Long idSalida) throws Exception;
}
