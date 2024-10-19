package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.RolDTO;
import pe.com.marbella.marketservice.service.RolService;

import java.util.List;

/**
 * Controlador para manejar las solicitudes relacionadas con los roles.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rol")
public class RolController {
    @Autowired
    RolService rolService;

    /**
     * Obtiene una lista de todos los roles.
     *
     * @return Una lista de roles.
     * @throws Exception Si ocurre un error al obtener los roles.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<RolDTO>> getAllRoles() throws Exception {
        List<RolDTO> medidas = rolService.listadoRoles();
        return new ResponseEntity<>(medidas, HttpStatus.OK);
    }

    /**
     * Obtiene un rol por su ID.
     *
     * @param id El ID del rol.
     * @return El rol con el ID especificado.
     * @throws Exception Si ocurre un error al obtener el rol.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<RolDTO> getRolById(@PathVariable Long id) throws Exception {
        RolDTO medidaDTO = rolService.buscarRol(id);
        return new ResponseEntity<>(medidaDTO, HttpStatus.OK);
    }
}
