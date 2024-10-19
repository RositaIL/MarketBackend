package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
import java.util.stream.Collectors;

/**
 * Implementación de la interfaz ProductoService.
 * Proporciona métodos para gestionar los productos.
 */
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

    /**
     * Mapea un objeto Producto a un objeto ProductoDTO.
     *
     * @param producto El objeto Producto a mapear.
     * @return El objeto ProductoDTO mapeado.
     */
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

    /**
     * Mapea un objeto ProductoDTO a un objeto Producto.
     *
     * @param productoDTO El objeto ProductoDTO a mapear.
     * @param medida      El objeto Medida asociado al producto.
     * @param marca       El objeto Marca asociado al producto.
     * @param categoria   El objeto Categoria asociado al producto.
     * @return El objeto Producto mapeado.
     */
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

    /**
     * Obtiene un objeto Medida por su ID.
     *
     * @param id El ID de la medida.
     * @return El objeto Medida encontrado.
     * @throws EntityNotFoundException Si la medida no se encuentra.
     */
    private Medida getMedida(Long id) {
        return medidaRepository.findByIdMedidaAndEstado(id,true).orElseThrow(() -> new EntityNotFoundException("Medida no encontrada"));
    }

    /**
     * Obtiene un objeto Marca por su ID.
     *
     * @param id El ID de la marca.
     * @return El objeto Marca encontrado.
     * @throws EntityNotFoundException Si la marca no se encuentra.
     */
    private Marca getMarca(Long id) {
        return marcaRepository.findByIdMarcaAndEstado(id,true).orElseThrow(() -> new EntityNotFoundException("Marca no encontrada"));
    }

    /**
     * Obtiene un objeto Categoria por su ID.
     *
     * @param id El ID de la categoría.
     * @return El objeto Categoria encontrado.
     * @throws EntityNotFoundException Si la categoría no se encuentra.
     */
    private Categoria getCategoria(Long id) {
        return categoriaRepository.findByIdCategoriaAndEstado(id,true).orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
    }

    /**
     * Obtiene una página de productos que coinciden con el nombre proporcionado.
     *
     * @param nombre   El nombre a buscar.
     * @param pageable Objeto Pageable para la paginación.
     * @return Una página de objetos ProductoDTO.
     * @throws Exception Si ocurre un error al obtener los productos.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDTO> findAll(String nombre, Pageable pageable) throws Exception{
        return productoRepository.findByNombreContainingAndEstado(nombre, true,pageable)
                .map(this::mapToDTO);
    }

    /**
     * Obtiene una página de productos que pertenecen a una categoría específica.
     *
     * @param categoria El ID de la categoría.
     * @param pageable  Objeto Pageable para la paginación.
     * @return Una página de objetos ProductoDTO.
     * @throws Exception Si ocurre un error al obtener los productos.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDTO> findAllByCategoria(Long categoria, Pageable pageable) throws Exception{
        return productoRepository.findAllByCategoria_IdCategoriaAndEstado(categoria,true,pageable)
                .map(this::mapToDTO);
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id El ID del producto.
     * @return El objeto ProductoDTO encontrado.
     * @throws Exception Si ocurre un error al obtener el producto.
     */
    @Override
    @Transactional(readOnly = true)
    public ProductoDTO findById(Long id) throws Exception{
        Producto producto = productoRepository.findByIdProAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
        return mapToDTO(producto);
    }

    /**
     * Guarda un nuevo producto.
     *
     * @param productoDTO El DTO del producto a guardar.
     * @return El DTO del producto guardado.
     * @throws Exception Si ocurre un error al guardar el producto.
     */
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

    /**
     * Actualiza un producto existente.
     *
     * @param productoDTO El DTO del producto con la información actualizada.
     * @return El DTO del producto actualizado.
     * @throws Exception Si ocurre un error al actualizar el producto.
     */
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

    /**
     * Elimina un producto.
     *
     * @param id El ID del producto a eliminar.
     * @throws Exception Si ocurre un error al eliminar el producto.
     */
    @Override
    @Transactional
    public void delete(Long id) throws Exception{
        Producto producto = productoRepository.findByIdProAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        producto.eliminar();
        productoRepository.save(producto);
    }

    /**
     * Encuentra todos los productos con bajo stock.
     *
     * @return Una lista de objetos ProductoDTO que representan los productos con bajo stock.
     * @throws Exception Si ocurre un error al obtener los productos.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findLowStockProducts() throws Exception {
        return productoRepository.findByStockActualAndEstado(true).stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }
}