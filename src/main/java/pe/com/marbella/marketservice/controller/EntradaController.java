package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.EntradaDTO;
import pe.com.marbella.marketservice.dto.validation.OnCreate;
import pe.com.marbella.marketservice.service.EntradaService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/entrada")
public class EntradaController {
    @Autowired
    private EntradaService entradaService;

    @GetMapping
    public ResponseEntity<List<EntradaDTO>> getAllEntradas() throws Exception {
        List<EntradaDTO> entradas = entradaService.findAll();
        return new ResponseEntity<>(entradas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaDTO> getEntradaById(@PathVariable Long id) throws Exception {
        EntradaDTO entradaDTO = entradaService.findById(id);
        return new ResponseEntity<>(entradaDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EntradaDTO> createEntrada(@Validated(OnCreate.class) @RequestBody EntradaDTO entradaDTO) throws Exception {
        EntradaDTO savedEntrada = entradaService.save(entradaDTO);
        return new ResponseEntity<>(savedEntrada, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrada(@PathVariable Long id) throws Exception {
        entradaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
