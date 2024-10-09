package pe.com.marbella.marketservice.service;

import org.springframework.data.domain.Pageable;
import pe.com.marbella.marketservice.dto.EntradaDTO;

import java.util.List;

public interface EntradaService {
    List<EntradaDTO> findAll(Pageable pageable) throws Exception;
    EntradaDTO findById(Long id) throws Exception;
    EntradaDTO save(EntradaDTO entity) throws Exception;
    void delete(Long idEntrada) throws Exception;
}
