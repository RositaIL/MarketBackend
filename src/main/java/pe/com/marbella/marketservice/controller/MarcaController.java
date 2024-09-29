package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.MarcaDTO;
import pe.com.marbella.marketservice.service.MarcaService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/marca")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<MarcaDTO>> getAllMarcas() throws Exception {
        List<MarcaDTO> marcas = marcaService.findAll();
        return new ResponseEntity<>(marcas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaDTO> getMarcaById(@PathVariable Long id) throws Exception {
        MarcaDTO marcaDTO = marcaService.findById(id);
        return new ResponseEntity<>(marcaDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MarcaDTO> createMarca(@RequestBody MarcaDTO marcaDTO) throws Exception {
        MarcaDTO savedMarca = marcaService.save(marcaDTO);
        return new ResponseEntity<>(savedMarca, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaDTO> updateMarca(@PathVariable Long id, @RequestBody MarcaDTO marcaDTO) throws Exception {
        if (!id.equals(marcaDTO.idMarca())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        MarcaDTO updatedMarca = marcaService.update(marcaDTO);
        return new ResponseEntity<>(updatedMarca, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarca(@PathVariable Long id) throws Exception {
        marcaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}