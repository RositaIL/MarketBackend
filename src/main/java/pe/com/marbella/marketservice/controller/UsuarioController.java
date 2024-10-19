package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

/**
 * Controlador para manejar las solicitudes relacionadas con los usuarios.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Obtiene una página de usuarios.
     *
     * @param nombre   El nombre del usuario a buscar (opcional).
     * @param pageable Objeto Pageable para la paginación.
     * @return Una página de usuarios que coinciden con los criterios de búsqueda.
     * @throws Exception Si ocurre un error al obtener los usuarios.
     */
    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDTO>> obtenerUsuarios(@RequestParam(defaultValue = "") String nombre , Pageable pageable) throws Exception {
        Page<UsuarioResponseDTO> usuarios = usuarioService.findAll(nombre, pageable);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return El usuario con el ID especificado.
     * @throws Exception Si ocurre un error al obtener el usuario.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(@PathVariable Long id) throws Exception {
        UsuarioResponseDTO usuarioDTO = usuarioService.findById(id);
        return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
    }

    /**
     * Crea un nuevo usuario.
     *
     * @param usuarioDTO El DTO del usuario a crear.
     * @return El usuario creado.
     * @throws Exception Si ocurre un error al crear el usuario.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Validated(OnCreate.class) @RequestBody UsuarioDTO usuarioDTO) throws Exception {
        UsuarioResponseDTO nuevoUsuario = usuarioService.save(usuarioDTO);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id          El ID del usuario a actualizar.
     * @param usuarioDTO El DTO del usuario con la información actualizada.
     * @return El usuario actualizado.
     * @throws Exception Si ocurre un error al actualizar el usuario.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody UsuarioDTO usuarioDTO) throws Exception {
        if (!id.equals(usuarioDTO.idUsuario())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UsuarioResponseDTO usuarioActualizado = usuarioService.update(usuarioDTO);
        return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
    }

    /**
     * Elimina un usuario.
     *
     * @param id El ID del usuario a eliminar.
     * @return Una respuesta vacía con código 200 OK si la eliminación fue exitosa.
     * @throws Exception Si ocurre un error al eliminar el usuario.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) throws Exception {
        usuarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
