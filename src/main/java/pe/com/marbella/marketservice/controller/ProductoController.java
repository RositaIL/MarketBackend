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

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<Page<ProductoDTO>> getAllProductos(@RequestParam(defaultValue = "") String nombre, Pageable pageable) throws Exception {
        Page<ProductoDTO> productos = productoService.findAll(nombre, pageable);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<Page<ProductoDTO>> getAllProductosByCategoria(@PathVariable Long categoria, Pageable pageable) throws Exception {
        Page<ProductoDTO> productos = productoService.findAllByCategoria(categoria, pageable);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) throws Exception {
        ProductoDTO productoDTO = productoService.findById(id);
        return new ResponseEntity<>(productoDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<ProductoDTO> createProducto(@Validated(OnCreate.class) @RequestBody ProductoDTO productoDTO) throws Exception {
        ProductoDTO savedProducto = productoService.save(productoDTO);
        return new ResponseEntity<>(savedProducto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody ProductoDTO productoDTO) throws Exception {
        if (!id.equals(productoDTO.idPro())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProductoDTO updatedProducto = productoService.update(productoDTO);
        return new ResponseEntity<>(updatedProducto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) throws Exception {
        productoService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
