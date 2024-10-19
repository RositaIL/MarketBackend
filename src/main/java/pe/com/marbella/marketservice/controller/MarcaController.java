package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.MarcaDTO;
import pe.com.marbella.marketservice.dto.validation.OnCreate;
import pe.com.marbella.marketservice.dto.validation.OnUpdate;
import pe.com.marbella.marketservice.service.MarcaService;

/**
 * Controlador para manejar las solicitudes relacionadas con las marcas.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/marca")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;

    /**
     * Obtiene una página de marcas.
     *
     * @param nombre   El nombre de la marca a buscar (opcional).
     * @param pageable Objeto Pageable para la paginación.
     * @return Una página de marcas que coinciden con los criterios de búsqueda.
     * @throws Exception Si ocurre un error al obtener las marcas.
     */
    @GetMapping
    public ResponseEntity<Page<MarcaDTO>> getAllMarcas(@RequestParam(defaultValue = "") String nombre, Pageable pageable) throws Exception {
        Page<MarcaDTO> marcas = marcaService.findAll(nombre, pageable);
        return new ResponseEntity<>(marcas, HttpStatus.OK);
    }

    /**
     * Obtiene una marca por su ID.
     *
     * @param id El ID de la marca.
     * @return La marca con el ID especificado.
     * @throws Exception Si ocurre un error al obtener la marca.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MarcaDTO> getMarcaById(@PathVariable Long id) throws Exception {
        MarcaDTO marcaDTO = marcaService.findById(id);
        return new ResponseEntity<>(marcaDTO, HttpStatus.OK);
    }

    /**
     * Crea una nueva marca.
     *
     * @param marcaDTO El DTO de la marca a crear.
     * @return La marca creada.
     * @throws Exception Si ocurre un error al crear la marca.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<MarcaDTO> createMarca(@Validated(OnCreate.class) @RequestBody MarcaDTO marcaDTO) throws Exception {
        MarcaDTO savedMarca = marcaService.save(marcaDTO);
        return new ResponseEntity<>(savedMarca, HttpStatus.CREATED);
    }

    /**
     * Actualiza una marca existente.
     *
     * @param id        El ID de la marca a actualizar.
     * @param marcaDTO El DTO de la marca con la información actualizada.
     * @return La marca actualizada.
     * @throws Exception Si ocurre un error al actualizar la marca.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<MarcaDTO> updateMarca(@PathVariable Long id,@Validated(OnUpdate.class) @RequestBody MarcaDTO marcaDTO) throws Exception {
        if (!id.equals(marcaDTO.idMarca())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        MarcaDTO updatedMarca = marcaService.update(marcaDTO);
        return new ResponseEntity<>(updatedMarca, HttpStatus.OK);
    }

    /**
     * Elimina una marca.
     *
     * @param id El ID de la marca a eliminar.
     * @return Una respuesta vacía con código 200 OK si la eliminación fue exitosa.
     * @throws Exception Si ocurre un error al eliminar la marca.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarca(@PathVariable Long id) throws Exception {
        marcaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}