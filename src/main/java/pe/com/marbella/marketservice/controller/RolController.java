package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.marbella.marketservice.dto.RolDTO;
import pe.com.marbella.marketservice.service.RolService;

import java.util.List;

@RestController
@RequestMapping("/rol")
public class RolController {
    @Autowired
    RolService rolService;

    @GetMapping
    public ResponseEntity<List<RolDTO>> getAllRoles() throws Exception {
        List<RolDTO> medidas = rolService.listadoRoles();
        return new ResponseEntity<>(medidas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolDTO> getRolById(@PathVariable Long id) throws Exception {
        RolDTO medidaDTO = rolService.buscarRol(id);
        return new ResponseEntity<>(medidaDTO, HttpStatus.OK);
    }
}
