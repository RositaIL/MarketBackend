package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.UsuarioDTO;
import pe.com.marbella.marketservice.dto.UsuarioResponseDTO;
import pe.com.marbella.marketservice.dto.validation.OnCreate;
import pe.com.marbella.marketservice.dto.validation.OnUpdate;
import pe.com.marbella.marketservice.service.UsuarioService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerUsuarios() throws Exception {
        List<UsuarioResponseDTO> usuarios = usuarioService.findAll();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(@PathVariable Long id) throws Exception {
        UsuarioResponseDTO usuarioDTO = usuarioService.findById(id);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Validated(OnCreate.class) @RequestBody UsuarioDTO usuarioDTO) throws Exception {
        UsuarioResponseDTO nuevoUsuario = usuarioService.save(usuarioDTO);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody UsuarioDTO usuarioDTO) throws Exception {
        if (!id.equals(usuarioDTO.idUsuario())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UsuarioResponseDTO usuarioActualizado = usuarioService.update(usuarioDTO);
        return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) throws Exception {
        usuarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
