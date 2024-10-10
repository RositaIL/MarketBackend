package pe.com.marbella.marketservice.service;

import java.util.List;
import org.springframework.data.domain.Page;
import pe.com.marbella.marketservice.dto.ProductoDTO;

import org.springframework.data.domain.Pageable;

public interface ProductoService {
    Page<ProductoDTO> findAll(String nombre, Pageable pageable) throws Exception;
    Page<ProductoDTO> findAllByCategoria(Long categoria, Pageable pageable) throws Exception;
    List<ProductoDTO> findLowStockProducts() throws Exception;
    ProductoDTO findById(Long id) throws Exception;
    ProductoDTO save(ProductoDTO marca) throws Exception;
    ProductoDTO update(ProductoDTO marca) throws Exception;
    void delete(Long id) throws Exception;
}
