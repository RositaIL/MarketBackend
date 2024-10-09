package pe.com.marbella.marketservice.service;

import org.springframework.data.domain.Page;
import pe.com.marbella.marketservice.dto.ProductoDTO;

import org.springframework.data.domain.Pageable;

public interface ProductoService {
    Page<ProductoDTO> findAll(Pageable pageable) throws Exception;
    Page<ProductoDTO> findAllByCategoria(Long categoria, Pageable pageable) throws Exception;
    ProductoDTO findById(Long id) throws Exception;
    ProductoDTO save(ProductoDTO marca) throws Exception;
    ProductoDTO update(ProductoDTO marca) throws Exception;
    void delete(Long id) throws Exception;
}
