package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.MedidaDTO;
import pe.com.marbella.marketservice.service.MedidaService;

import java.util.List;

@RestController
@RequestMapping("/medida")
public class MedidaController {
    @Autowired
    private MedidaService medidaService;

    @GetMapping
    public ResponseEntity<List<MedidaDTO>> getAllMedidas() throws Exception {
        List<MedidaDTO> medidas = medidaService.findAll();
        return new ResponseEntity<>(medidas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedidaDTO> getMedidaById(@PathVariable Long id) throws Exception {
        MedidaDTO medidaDTO = medidaService.findById(id);
        return new ResponseEntity<>(medidaDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MedidaDTO> createMedida(@RequestBody MedidaDTO medidaDTO) throws Exception {
        MedidaDTO savedMedida = medidaService.save(medidaDTO);
        return new ResponseEntity<>(savedMedida, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedidaDTO> updateMedida(@PathVariable Long id, @RequestBody MedidaDTO medidaDTO) throws Exception {
        if (!id.equals(medidaDTO.idMedida())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        MedidaDTO updatedMedida = medidaService.update(medidaDTO);
        return new ResponseEntity<>(updatedMedida, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedida(@PathVariable Long id) throws Exception {
        medidaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
