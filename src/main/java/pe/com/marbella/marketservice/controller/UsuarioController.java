package pe.com.marbella.marketservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.UsuarioDTO;
import pe.com.marbella.marketservice.service.UsuarioService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuarios() throws Exception {
        List<UsuarioDTO> usuarios = usuarioService.findAll();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Long id) throws Exception {
        UsuarioDTO usuarioDTO = usuarioService.findById(id);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) throws Exception {
        UsuarioDTO nuevoUsuario = usuarioService.save(usuarioDTO);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) throws Exception {
        if (!id.equals(usuarioDTO.idUsuario())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UsuarioDTO usuarioActualizado = usuarioService.update(usuarioDTO);
        return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) throws Exception {
        usuarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
