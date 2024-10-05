package pe.com.marbella.marketservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.ProductoDTO;
import pe.com.marbella.marketservice.service.ProductoService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos(@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        List<ProductoDTO> productos = productoService.findAll(pageable);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) throws Exception {
        ProductoDTO productoDTO = productoService.findById(id);
        return new ResponseEntity<>(productoDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<ProductoDTO> createProducto(@Valid @RequestBody ProductoDTO productoDTO) throws Exception {
        ProductoDTO savedProducto = productoService.save(productoDTO);
        return new ResponseEntity<>(savedProducto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id, @Valid @RequestBody ProductoDTO productoDTO) throws Exception {
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
