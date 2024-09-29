package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.dto.ProductoDTO;

import java.util.List;

public interface ProductoService {
    List<ProductoDTO> findAll() throws Exception;
    ProductoDTO findById(Long id) throws Exception;
    ProductoDTO save(ProductoDTO marca) throws Exception;
    ProductoDTO update(ProductoDTO marca) throws Exception;
    void delete(Long id) throws Exception;
}
