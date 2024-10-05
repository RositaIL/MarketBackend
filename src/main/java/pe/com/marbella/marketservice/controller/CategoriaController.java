package pe.com.marbella.marketservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.CategoriaDTO;
import pe.com.marbella.marketservice.service.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() throws Exception {
        List<CategoriaDTO> categorias = categoriaService.listadoCategoria();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getCategoriaById(@PathVariable Long id) throws Exception {
        CategoriaDTO categoriaDTO = categoriaService.buscarCategoria(id);
        return new ResponseEntity<>(categoriaDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> createCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) throws Exception {
        CategoriaDTO savedCategoria = categoriaService.save(categoriaDTO);
        return new ResponseEntity<>(savedCategoria, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> updateCategoria(@PathVariable Long id, @Valid @RequestBody CategoriaDTO categoriaDTO) throws Exception {
        if (!id.equals(categoriaDTO.idCategoria())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CategoriaDTO updatedCategoria = categoriaService.update(categoriaDTO);
        return new ResponseEntity<>(updatedCategoria, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) throws Exception {
        categoriaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
