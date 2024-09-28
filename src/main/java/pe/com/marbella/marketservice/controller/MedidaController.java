package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.model.Medida;
import pe.com.marbella.marketservice.service.MedidaService;

import java.util.List;

@RestController
@RequestMapping("/medida")
public class MedidaController {
    @Autowired
    private MedidaService medidaService;

    @GetMapping
    public ResponseEntity<List<Medida>> obtenerMedidas() {
        try {
            List<Medida> medidas = medidaService.findAll();
            if (medidas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(medidas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medida> obtenerMedidaPorId(@PathVariable("id") Long id) {
        try {
            Medida medida = medidaService.findById(id);
            return new ResponseEntity<>(medida, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Medida> crearMedida(@RequestBody Medida medida) {
        try {
            Medida nuevaMedida = medidaService.save(medida);
            return new ResponseEntity<>(nuevaMedida, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medida> actualizarMedida(@PathVariable("id") Long id, @RequestBody Medida medida) {
        try {
            medida.setIdMedida(id);
            Medida medidaActualizada = medidaService.update(medida);
            return new ResponseEntity<>(medidaActualizada, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMedida(@PathVariable("id") Long id) {
        try {
            boolean eliminado = medidaService.delete(id);
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
