package pe.com.marbella.marketservice.service;

import org.springframework.transaction.annotation.Transactional;
import pe.com.marbella.marketservice.dto.ProductoDTO;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface ProductoService {
    List<ProductoDTO> findAll(Pageable pageable) throws Exception;

    List<ProductoDTO> findAllByCategoria(Long categoria, Pageable pageable) throws Exception;

    ProductoDTO findById(Long id) throws Exception;
    ProductoDTO save(ProductoDTO marca) throws Exception;
    ProductoDTO update(ProductoDTO marca) throws Exception;
    void delete(Long id) throws Exception;
}
