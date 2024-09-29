package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.ProductoDTO;
import pe.com.marbella.marketservice.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() throws Exception {
        List<ProductoDTO> productos = productoService.findAll();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) throws Exception {
        ProductoDTO productoDTO = productoService.findById(id);
        return new ResponseEntity<>(productoDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> createProducto(@RequestBody ProductoDTO productoDTO) throws Exception {
        ProductoDTO savedProducto = productoService.save(productoDTO);
        return new ResponseEntity<>(savedProducto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) throws Exception {
        if (!id.equals(productoDTO.idPro())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProductoDTO updatedProducto = productoService.update(productoDTO);
        return new ResponseEntity<>(updatedProducto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) throws Exception {
        productoService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
