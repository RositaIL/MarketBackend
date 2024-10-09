package pe.com.marbella.marketservice.service;

import org.springframework.data.domain.Pageable;
import pe.com.marbella.marketservice.dto.MedidaDTO;

import java.util.List;

public interface MedidaService {
    List<MedidaDTO> findAll(Pageable pageable) throws Exception;
    MedidaDTO findById(Long id) throws Exception;
    MedidaDTO save(MedidaDTO marca) throws Exception;
    MedidaDTO update(MedidaDTO marca) throws Exception;
    void delete(Long id) throws Exception;
}
