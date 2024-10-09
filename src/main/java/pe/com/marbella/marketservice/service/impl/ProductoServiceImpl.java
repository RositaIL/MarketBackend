package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.dto.ProductoDTO;
import pe.com.marbella.marketservice.model.Categoria;
import pe.com.marbella.marketservice.model.Marca;
import pe.com.marbella.marketservice.model.Medida;
import pe.com.marbella.marketservice.model.Producto;
import pe.com.marbella.marketservice.repository.CategoriaRepository;
import pe.com.marbella.marketservice.repository.MarcaRepository;
import pe.com.marbella.marketservice.repository.MedidaRepository;
import pe.com.marbella.marketservice.repository.ProductoRepository;
import pe.com.marbella.marketservice.service.ProductoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    MedidaRepository medidaRepository;
    @Autowired
    MarcaRepository marcaRepository;
    @Autowired
    CategoriaRepository categoriaRepository;

    private ProductoDTO mapToDTO(Producto producto) {
        return new ProductoDTO(
                producto.getIdPro(),
                producto.getNombrePro(),
                producto.getDescripcionPro(),
                producto.getPrecioPro(),
                producto.getStockActual(),
                producto.getStockMin(),
                producto.getMedida().getIdMedida(),
                producto.getMarca().getIdMarca(),
                producto.getCategoria().getIdCategoria()
        );
    }

    private Producto mapToEntity(ProductoDTO productoDTO, Medida medida, Marca marca, Categoria categoria) {
        return new Producto(
                productoDTO.idPro(),
                productoDTO.nombrePro(),
                productoDTO.descripcionPro(),
                productoDTO.precioPro(),
                productoDTO.stockActual(),
                productoDTO.stockMin(),
                medida,
                marca,
                categoria,
                true
        );
    }

    private Medida getMedida(Long id) {
        return medidaRepository.findByIdMedidaAndEstado(id,true).orElseThrow(() -> new EntityNotFoundException("Medida no encontrada"));
    }

    private Marca getMarca(Long id) {
        return marcaRepository.findByIdMarcaAndEstado(id,true).orElseThrow(() -> new EntityNotFoundException("Marca no encontrada"));
    }

    private Categoria getCategoria(Long id) {
        return categoriaRepository.findByIdCategoriaAndEstado(id,true).orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findAll(Pageable pageable) throws Exception{
        List<Producto> productos = productoRepository.findByEstado(true,pageable);
        return productos.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findAllByCategoria(Long categoria, Pageable pageable) throws Exception{
        List<Producto> productos = productoRepository.findAllByCategoria_IdCategoriaAndEstado(categoria,true,pageable);
        return productos.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDTO findById(Long id) throws Exception{
        Producto producto = productoRepository.findByIdProAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
        return mapToDTO(producto);
    }

    @Override
    @Transactional
    public ProductoDTO save(ProductoDTO productoDTO) throws Exception{
        Medida medida = getMedida(productoDTO.idMedida());
        Marca marca = getMarca(productoDTO.idMarca());
        Categoria categoria = getCategoria(productoDTO.idCategoria());

        Producto producto = mapToEntity(productoDTO, medida, marca, categoria);
        Producto respuesta = productoRepository.save(producto);
        return mapToDTO(respuesta);
    }

    @Override
    @Transactional
    public ProductoDTO update(ProductoDTO productoDTO) throws Exception{
        Producto producto = productoRepository.findByIdProAndEstado(productoDTO.idPro(), true)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        producto.setNombrePro(productoDTO.nombrePro());
        producto.setDescripcionPro(productoDTO.descripcionPro());
        producto.setPrecioPro(productoDTO.precioPro());
        producto.setStockActual(productoDTO.stockActual());
        producto.setStockMin(productoDTO.stockMin());

        Medida medida = getMedida(productoDTO.idMedida());
        Marca marca = getMarca(productoDTO.idMarca());
        Categoria categoria = getCategoria(productoDTO.idCategoria());

        producto.setMedida(medida);
        producto.setMarca(marca);
        producto.setCategoria(categoria);

        Producto respuesta = productoRepository.save(producto);
        return mapToDTO(respuesta);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception{
        Producto producto = productoRepository.findByIdProAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        producto.eliminar();
        productoRepository.save(producto);
    }
}