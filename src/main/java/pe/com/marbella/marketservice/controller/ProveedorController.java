package pe.com.marbella.marketservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> getAllProveedores(@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        List<ProveedorDTO> proveedores = proveedorService.findAll(pageable);
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> getProveedorById(@PathVariable Long id) throws Exception {
        ProveedorDTO proveedorDTO = proveedorService.findById(id);
        return new ResponseEntity<>(proveedorDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<ProveedorDTO> createProveedor(@Valid @RequestBody ProveedorDTO proveedorDTO) throws Exception {
        ProveedorDTO savedProveedor = proveedorService.save(proveedorDTO);
        return new ResponseEntity<>(savedProveedor, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> updateProveedor(@PathVariable Long id, @Valid @RequestBody ProveedorDTO proveedorDTO) throws Exception {
        if (!id.equals(proveedorDTO.idProveedor())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProveedorDTO updatedProveedor = proveedorService.update(proveedorDTO);
        return new ResponseEntity<>(updatedProveedor, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) throws Exception {
        proveedorService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
