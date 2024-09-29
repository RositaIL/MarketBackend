package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.model.Categoria;
import pe.com.marbella.marketservice.service.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerCategorias() throws Exception {
        List<Categoria> categorias = categoriaService.listadoCategoria();
        if (categorias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable("id") long id) throws Exception {
        Categoria categoria = categoriaService.buscarCategoria(id);
        if (categoria == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Categoria> guardarCategoria(@RequestBody Categoria categoria) throws Exception {
        Categoria nuevaCategoria = categoriaService.save(categoria);
        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable("id") long id, @RequestBody Categoria categoria) throws Exception {
        Categoria categoriaExistente = categoriaService.buscarCategoria(id);
        if (categoriaExistente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        categoria.setIdCategoria(id);
        Categoria categoriaActualizada = categoriaService.update(categoria);
        return new ResponseEntity<>(categoriaActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarCategoria(@PathVariable("id") long id) throws Exception {
        boolean eliminado = categoriaService.delete(id);
        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
