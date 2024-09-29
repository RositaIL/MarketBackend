package pe.com.marbella.marketservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.ProveedorDTO;
import pe.com.marbella.marketservice.service.ProveedorService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/proveedor")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> getAllProveedores() throws Exception {
        List<ProveedorDTO> proveedores = proveedorService.findAll();
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> getProveedorById(@PathVariable Long id) throws Exception {
        ProveedorDTO proveedorDTO = proveedorService.findById(id);
        return new ResponseEntity<>(proveedorDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProveedorDTO> createProveedor(@Valid @RequestBody ProveedorDTO proveedorDTO) throws Exception {
        ProveedorDTO savedProveedor = proveedorService.save(proveedorDTO);
        return new ResponseEntity<>(savedProveedor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> updateProveedor(@PathVariable Long id, @Valid @RequestBody ProveedorDTO proveedorDTO) throws Exception {
        if (!id.equals(proveedorDTO.idProveedor())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProveedorDTO updatedProveedor = proveedorService.update(proveedorDTO);
        return new ResponseEntity<>(updatedProveedor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) throws Exception {
        proveedorService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
