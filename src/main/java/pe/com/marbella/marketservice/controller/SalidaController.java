package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.SalidaDTO;
import pe.com.marbella.marketservice.dto.validation.OnCreate;
import pe.com.marbella.marketservice.service.SalidaService;

/**
 * Controlador para manejar las solicitudes relacionadas con las solicitudes de salida.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/salida")
public class SalidaController {
    @Autowired
    private SalidaService salidaService;

    /**
     * Obtiene una página de salidas.
     *
     * @param pageable Objeto Pageable para la paginación.
     * @return Una página de salidas.
     * @throws Exception Si ocurre un error al obtener las salidas.
     */
    @GetMapping
    public ResponseEntity<Page<SalidaDTO>> getAllSalidas(Pageable pageable) throws Exception {
        Page<SalidaDTO> salidas = salidaService.findAll(pageable);
        return new ResponseEntity<>(salidas, HttpStatus.OK);
    }

    /**
     * Obtiene una salida por su ID.
     *
     * @param id El ID de la salida.
     * @return La salida con el ID especificado.
     * @throws Exception Si ocurre un error al obtener la salida.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SalidaDTO> getSalidaById(@PathVariable Long id) throws Exception {
        SalidaDTO salidaDTO = salidaService.findById(id);
        return new ResponseEntity<>(salidaDTO, HttpStatus.OK);
    }

    /**
     * Crea una nueva salida.
     *
     * @param salidaDTO El DTO de la salida a crear.
     * @return La salida creada.
     * @throws Exception Si ocurre un error al crear la salida.
     */
    @PostMapping
    public ResponseEntity<SalidaDTO> createSalida(@Validated(OnCreate.class) @RequestBody SalidaDTO salidaDTO) throws Exception {
        SalidaDTO savedSalida = salidaService.save(salidaDTO);
        return new ResponseEntity<>(savedSalida, HttpStatus.CREATED);
    }

    /**
     * Elimina una salida.
     *
     * @param id El ID de la salida a eliminar.
     * @return Una respuesta vacía con código 200 OK si la eliminación fue exitosa.
     * @throws Exception Si ocurre un error al eliminar la salida.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalida(@PathVariable Long id) throws Exception {
        salidaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
