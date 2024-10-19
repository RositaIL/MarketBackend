package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.EntradaDTO;
import pe.com.marbella.marketservice.dto.validation.OnCreate;
import pe.com.marbella.marketservice.service.EntradaService;

/**
 * Controlador para manejar las solicitudes relacionadas con las entradas.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/entrada")
public class EntradaController {
    @Autowired
    private EntradaService entradaService;

    /**
     * Obtiene una página de entradas.
     *
     * @param pageable Objeto Pageable para la paginación.
     * @return Una página de entradas.
     * @throws Exception Si ocurre un error al obtener las entradas.
     */
    @GetMapping
    public ResponseEntity<Page<EntradaDTO>> getAllEntradas(Pageable pageable) throws Exception {
        Page<EntradaDTO> entradas = entradaService.findAll(pageable);
        return new ResponseEntity<>(entradas, HttpStatus.OK);
    }

    /**
     * Obtiene una entrada por su ID.
     *
     * @param id El ID de la entrada.
     * @return La entrada con el ID especificado.
     * @throws Exception Si ocurre un error al obtener la entrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntradaDTO> getEntradaById(@PathVariable Long id) throws Exception {
        EntradaDTO entradaDTO = entradaService.findById(id);
        return new ResponseEntity<>(entradaDTO, HttpStatus.OK);
    }

    /**
     * Crea una nueva entrada.
     *
     * @param entradaDTO El DTO de la entrada a crear.
     * @return La entrada creada.
     * @throws Exception Si ocurre un error al crear la entrada.
     */
    @PostMapping
    public ResponseEntity<EntradaDTO> createEntrada(@Validated(OnCreate.class) @RequestBody EntradaDTO entradaDTO) throws Exception {
        EntradaDTO savedEntrada = entradaService.save(entradaDTO);
        return new ResponseEntity<>(savedEntrada, HttpStatus.CREATED);
    }

    /**
     * Elimina una entrada.
     *
     * @param id El ID de la entrada a eliminar.
     * @return Una respuesta vacía con código 200 OK si la eliminación fue exitosa.
     * @throws Exception Si ocurre un error al eliminar la entrada.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrada(@PathVariable Long id) throws Exception {
        entradaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
