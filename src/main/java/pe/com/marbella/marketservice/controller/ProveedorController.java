package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.ProveedorDTO;
import pe.com.marbella.marketservice.dto.validation.OnCreate;
import pe.com.marbella.marketservice.dto.validation.OnUpdate;
import pe.com.marbella.marketservice.service.ProveedorService;

/**
 * Controlador para manejar las solicitudes relacionadas con los proveedores.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/proveedor")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    /**
     * Obtiene una página de proveedores.
     *
     * @param nombre   El nombre del proveedor a buscar (opcional).
     * @param pageable Objeto Pageable para la paginación.
     * @return Una página de proveedores que coinciden con los criterios de búsqueda.
     * @throws Exception Si ocurre un error al obtener los proveedores.
     */
    @GetMapping
    public ResponseEntity<Page<ProveedorDTO>> getAllProveedores(@RequestParam(defaultValue = "") String nombre, Pageable pageable) throws Exception {
        Page<ProveedorDTO> proveedores = proveedorService.findAll(nombre, pageable);
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }

    /**
     * Obtiene un proveedor por su ID.
     *
     * @param id El ID del proveedor.
     * @return El proveedor con el ID especificado.
     * @throws Exception Si ocurre un error al obtener el proveedor.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> getProveedorById(@PathVariable Long id) throws Exception {
        ProveedorDTO proveedorDTO = proveedorService.findById(id);
        return new ResponseEntity<>(proveedorDTO, HttpStatus.OK);
    }

    /**
     * Crea un nuevo proveedor.
     *
     * @param proveedorDTO El DTO del proveedor a crear.
     * @return El proveedor creado.
     * @throws Exception Si ocurre un error al crear el proveedor.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<ProveedorDTO> createProveedor(@Validated(OnCreate.class) @RequestBody ProveedorDTO proveedorDTO) throws Exception {
        ProveedorDTO savedProveedor = proveedorService.save(proveedorDTO);
        return new ResponseEntity<>(savedProveedor, HttpStatus.CREATED);
    }

    /**
     * Actualiza un proveedor existente.
     *
     * @param id  El ID del proveedor a actualizar.
     * @param proveedorDTO El DTO del proveedor con la información actualizada.
     * @return El proveedor actualizado.
     * @throws Exception Si ocurre un error al actualizar el proveedor.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> updateProveedor(@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody ProveedorDTO proveedorDTO) throws Exception {
        if (!id.equals(proveedorDTO.idProveedor())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProveedorDTO updatedProveedor = proveedorService.update(proveedorDTO);
        return new ResponseEntity<>(updatedProveedor, HttpStatus.OK);
    }

    /**
     * Elimina un proveedor.
     *
     * @param id El ID del proveedor a eliminar.
     * @return Una respuesta vacía con código 200 OK si la eliminación fue exitosa.
     * @throws Exception Si ocurre un error al eliminar el proveedor.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) throws Exception {
        proveedorService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
