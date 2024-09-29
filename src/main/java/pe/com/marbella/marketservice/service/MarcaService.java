package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.dto.MarcaDTO;

import java.util.List;

public interface MarcaService {

    List<MarcaDTO> findAll() throws Exception;
    MarcaDTO findById(Long id) throws Exception;
    MarcaDTO save(MarcaDTO marca) throws Exception;
    MarcaDTO update(MarcaDTO marca) throws Exception;
    void delete(Long id) throws Exception;
}
