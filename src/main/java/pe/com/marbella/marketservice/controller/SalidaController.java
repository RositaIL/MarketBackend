package pe.com.marbella.marketservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.SalidaDTO;
import pe.com.marbella.marketservice.service.SalidaService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/salida")
public class SalidaController {
    @Autowired
    private SalidaService salidaService;

    @GetMapping
    public ResponseEntity<List<SalidaDTO>> getAllSalidas() throws Exception {
        List<SalidaDTO> salidas = salidaService.findAll();
        return new ResponseEntity<>(salidas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalidaDTO> getSalidaById(@PathVariable Long id) throws Exception {
        SalidaDTO salidaDTO = salidaService.findById(id);
        return new ResponseEntity<>(salidaDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SalidaDTO> createSalida(@Valid @RequestBody SalidaDTO salidaDTO) throws Exception {
        SalidaDTO savedSalida = salidaService.save(salidaDTO);
        return new ResponseEntity<>(savedSalida, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalida(@PathVariable Long id) throws Exception {
        salidaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}