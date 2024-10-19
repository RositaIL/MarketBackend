package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.ProductoDTO;
import pe.com.marbella.marketservice.dto.validation.OnCreate;
import pe.com.marbella.marketservice.dto.validation.OnUpdate;
import pe.com.marbella.marketservice.service.ProductoService;
import java.util.List;

/**
 * Controlador para manejar las solicitudes relacionadas con los productos.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    /**
     * Obtiene una página de productos.
     *
     * @param nombre   El nombre del producto a buscar (opcional).
     * @param pageable Objeto Pageable para la paginación.
     * @return Una página de productos que coinciden con los criterios de búsqueda.
     * @throws Exception Si ocurre un error al obtener los productos.
     */
    @GetMapping
    public ResponseEntity<Page<ProductoDTO>> getAllProductos(@RequestParam(defaultValue = "") String nombre, Pageable pageable) throws Exception {
        Page<ProductoDTO> productos = productoService.findAll(nombre, pageable);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    /**
     * Obtiene una página de productos por categoría.
     *
     * @param categoria El ID de la categoría.
     * @param pageable  Objeto Pageable para la paginación.
     * @return Una página de productos que pertenecen a la categoría especificada.
     * @throws Exception Si ocurre un error al obtener los productos.
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<Page<ProductoDTO>> getAllProductosByCategoria(@PathVariable Long categoria, Pageable pageable) throws Exception {
        Page<ProductoDTO> productos = productoService.findAllByCategoria(categoria, pageable);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id El ID del producto.
     * @return El producto con el ID especificado.
     * @throws Exception Si ocurre un error al obtener el producto.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) throws Exception {
        ProductoDTO productoDTO = productoService.findById(id);
        return new ResponseEntity<>(productoDTO, HttpStatus.OK);
    }

    /**
     * Crea un nuevo producto.
     *
     * @param productoDTO El DTO del producto a crear.
     * @return El producto creado.
     * @throws Exception Si ocurre un error al crear el producto.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<ProductoDTO> createProducto(@Validated(OnCreate.class) @RequestBody ProductoDTO productoDTO) throws Exception {
        ProductoDTO savedProducto = productoService.save(productoDTO);
        return new ResponseEntity<>(savedProducto, HttpStatus.CREATED);
    }

    /**
     * Actualiza un producto existente.
     *
     * @param id          El ID del producto a actualizar.
     * @param productoDTO El DTO del producto con la información actualizada.
     * @return El producto actualizado.
     * @throws Exception Si ocurre un error al actualizar el producto.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody ProductoDTO productoDTO) throws Exception {
        if (!id.equals(productoDTO.idPro())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProductoDTO updatedProducto = productoService.update(productoDTO);
        return new ResponseEntity<>(updatedProducto, HttpStatus.OK);
    }

    /**
     * Elimina un producto.
     *
     * @param id El ID del producto a eliminar.
     * @return Una respuesta vacía con código 200 OK si la eliminación fue exitosa.
     * @throws Exception Si ocurre un error al eliminar el producto.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) throws Exception {
        productoService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Obtiene una lista de productos con bajo stock.
     *
     * @return Una lista de productos con bajo stock.
     * @throws Exception Si ocurre un error al obtener los productos.
     */
    @GetMapping("/reporte")
    public ResponseEntity<List<ProductoDTO>> getLowStockProducts() throws Exception {
        List<ProductoDTO> productos = productoService.findLowStockProducts();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
}
