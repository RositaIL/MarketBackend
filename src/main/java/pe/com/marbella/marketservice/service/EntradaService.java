package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.dto.EntradaDTO;

import java.util.List;

public interface EntradaService {
    List<EntradaDTO> findAll() throws Exception;
    EntradaDTO findById(Long id) throws Exception;
    EntradaDTO save(EntradaDTO entity) throws Exception;
    void delete(Long idEntrada) throws Exception;
}
