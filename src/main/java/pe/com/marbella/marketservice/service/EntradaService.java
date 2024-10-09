package pe.com.marbella.marketservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.marbella.marketservice.dto.EntradaDTO;

public interface EntradaService {
    Page<EntradaDTO> findAll(Pageable pageable) throws Exception;
    EntradaDTO findById(Long id) throws Exception;
    EntradaDTO save(EntradaDTO entity) throws Exception;
    void delete(Long idEntrada) throws Exception;
}
