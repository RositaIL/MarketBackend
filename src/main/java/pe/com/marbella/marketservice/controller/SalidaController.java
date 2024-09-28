package pe.com.marbella.marketservice.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.SalidaDTO;
import pe.com.marbella.marketservice.model.Salida;
import pe.com.marbella.marketservice.service.SalidaService;

import java.util.List;

@RestController
@RequestMapping("/salida")
public class SalidaController {
    @Autowired
    private SalidaService salidaService;

    @GetMapping
    public ResponseEntity<List<Salida>> obtenerSalidas() {
        try {
            List<Salida> salidas = salidaService.findAll();
            if (salidas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(salidas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salida> obtenerSalidaPorId(@PathVariable("id") Long id) {
        try {
            Salida salida = salidaService.findById(id);
            return new ResponseEntity<>(salida, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Salida> crearSalida(@RequestBody SalidaDTO salidaDTO) {
        try {
            Salida nuevaSalida = salidaService.save(salidaDTO);
            return new ResponseEntity<>(nuevaSalida, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSalida(@PathVariable("id") Long id) {
        try {
            salidaService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
