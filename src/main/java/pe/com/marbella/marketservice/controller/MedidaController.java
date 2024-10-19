package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.MedidaDTO;
import pe.com.marbella.marketservice.dto.validation.OnCreate;
import pe.com.marbella.marketservice.dto.validation.OnUpdate;
import pe.com.marbella.marketservice.service.MedidaService;

/**
 * Controlador para manejar las solicitudes relacionadas con las unidades de medida.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/medida")
public class MedidaController {
    @Autowired
    private MedidaService medidaService;

    /**
     * Obtiene una página de unidades de medida.
     *
     * @param nombre   El nombre de la unidad de medida a buscar (opcional).
     * @param pageable Objeto Pageable para la paginación.
     * @return Una página de unidades de medida que coinciden con los criterios de búsqueda.
     * @throws Exception Si ocurre un error al obtener las unidades de medida.
     */
    @GetMapping
    public ResponseEntity<Page<MedidaDTO>> getAllMedidas(@RequestParam(defaultValue = "") String nombre, Pageable pageable) throws Exception {
        Page<MedidaDTO> medidas = medidaService.findAll(nombre, pageable);
        return new ResponseEntity<>(medidas, HttpStatus.OK);
    }

    /**
     * Obtiene una unidad de medida por su ID.
     *
     * @param id El ID de la unidad de medida.
     * @return La unidad de medida con el ID especificado.
     * @throws Exception Si ocurre un error al obtener la unidad de medida.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MedidaDTO> getMedidaById(@PathVariable Long id) throws Exception {
        MedidaDTO medidaDTO = medidaService.findById(id);
        return new ResponseEntity<>(medidaDTO, HttpStatus.OK);
    }

    /**
     * Crea una nueva unidad de medida.
     *
     * @param medidaDTO El DTO de la unidad de medida a crear.
     * @return La unidad de medida creada.
     * @throws Exception Si ocurre un error al crear la unidad de medida.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<MedidaDTO> createMedida(@Validated(OnCreate.class) @RequestBody MedidaDTO medidaDTO) throws Exception {
        MedidaDTO savedMedida = medidaService.save(medidaDTO);
        return new ResponseEntity<>(savedMedida, HttpStatus.CREATED);
    }

    /**
     * Actualiza una unidad de medida existente.
     *
     * @param id        El ID de la unidad de medida a actualizar.
     * @param medidaDTO El DTO de la unidad de medida con la información actualizada.
     * @return La unidad de medida actualizada.
     * @throws Exception Si ocurre un error al actualizar la unidad de medida.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<MedidaDTO> updateMedida(@PathVariable Long id,@Validated(OnUpdate.class) @RequestBody MedidaDTO medidaDTO) throws Exception {
        if (!id.equals(medidaDTO.idMedida())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        MedidaDTO updatedMedida = medidaService.update(medidaDTO);
        return new ResponseEntity<>(updatedMedida, HttpStatus.OK);
    }

    /**
     * Elimina una unidad de medida.
     *
     * @param id El ID de la unidad de medida a eliminar.
     * @return Una respuesta vacía con código 200 OK si la eliminación fue exitosa.
     * @throws Exception Si ocurre un error al eliminar la unidad de medida.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedida(@PathVariable Long id) throws Exception {
        medidaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
