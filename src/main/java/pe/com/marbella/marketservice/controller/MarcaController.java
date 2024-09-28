package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.model.Marca;
import pe.com.marbella.marketservice.service.MarcaService;

import java.util.List;

@RestController
@RequestMapping("/marca")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<Marca>> obtenerMarcas() {
        try {
            List<Marca> marcas = marcaService.findAll();
            if (marcas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(marcas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> obtenerMarcaPorId(@PathVariable("id") Long id) {
        try {
            Marca marca = marcaService.findById(id);
            return new ResponseEntity<>(marca, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Guardar una nueva marca
    @PostMapping
    public ResponseEntity<Marca> crearMarca(@RequestBody Marca marca) {
        try {
            Marca nuevaMarca = marcaService.save(marca);
            return new ResponseEntity<>(nuevaMarca, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marca> actualizarMarca(@PathVariable("id") Long id, @RequestBody Marca marca) {
        try {
            Marca marcaExistente = marcaService.findById(id);
            if (marcaExistente == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            marca.setIdMarca(id);
            Marca marcaActualizada = marcaService.update(marca);

            return new ResponseEntity<>(marcaActualizada, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMarca(@PathVariable("id") Long id) {
        try {
            boolean eliminado = marcaService.delete(id);
            if (eliminado) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}