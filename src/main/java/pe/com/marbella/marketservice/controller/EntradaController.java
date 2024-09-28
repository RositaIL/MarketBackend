package pe.com.marbella.marketservice.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.EntradaDTO;
import pe.com.marbella.marketservice.model.Entrada;
import pe.com.marbella.marketservice.service.EntradaService;

import java.util.List;

@RestController
@RequestMapping("/entrada")
public class EntradaController {
    @Autowired
    private EntradaService entradaService;

    @GetMapping
    public ResponseEntity<List<Entrada>> obtenerEntradas() {
        try {
            List<Entrada> entradas = entradaService.findAll();
            if (entradas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(entradas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entrada> obtenerEntradaPorId(@PathVariable("id") Long id) {
        try {
            Entrada entrada = entradaService.findById(id);
            return new ResponseEntity<>(entrada, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Entrada> crearEntrada(@RequestBody EntradaDTO entradaDTO) {
        try {
            Entrada nuevaEntrada = entradaService.save(entradaDTO);
            return new ResponseEntity<>(nuevaEntrada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEntrada(@PathVariable("id") Long id) {
        try {
            entradaService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
