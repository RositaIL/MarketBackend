package pe.com.marbella.marketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.com.marbella.marketservice.dto.CategoriaDTO;
import pe.com.marbella.marketservice.dto.validation.OnCreate;
import pe.com.marbella.marketservice.dto.validation.OnUpdate;
import pe.com.marbella.marketservice.service.CategoriaService;

/**
 * Controlador para manejar las solicitudes relacionadas con las categorías.
 */
@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    /**
     * Obtiene una página de categorías.
     *
     * @param nombre   El nombre de la categoría a buscar (opcional).
     * @param pageable Objeto Pageable para la paginación.
     * @return Una página de categorías que coinciden con los criterios de búsqueda.
     * @throws Exception Si ocurre un error al obtener las categorías.
     */
    @GetMapping
    public ResponseEntity<Page<CategoriaDTO>> getAllCategorias(@RequestParam(defaultValue = "") String nombre, Pageable pageable) throws Exception {
        Page<CategoriaDTO> categorias = categoriaService.listadoCategoria(nombre, pageable);
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    /**
     * Obtiene una categoría por su ID.
     *
     * @param id El ID de la categoría.
     * @return La categoría con el ID especificado.
     * @throws Exception Si ocurre un error al obtener la categoría.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getCategoriaById(@PathVariable Long id) throws Exception {
        CategoriaDTO categoriaDTO = categoriaService.buscarCategoria(id);
        return new ResponseEntity<>(categoriaDTO, HttpStatus.OK);
    }

    /**
     * Crea una nueva categoría.
     *
     * @param categoriaDTO El DTO de la categoría a crear.
     * @return La categoría creada.
     * @throws Exception Si ocurre un error al crear la categoría.
     */
    @PostMapping
    public ResponseEntity<CategoriaDTO> createCategoria(@Validated(OnCreate.class) @RequestBody CategoriaDTO categoriaDTO) throws Exception {
        CategoriaDTO savedCategoria = categoriaService.save(categoriaDTO);
        return new ResponseEntity<>(savedCategoria, HttpStatus.CREATED);
    }

    /**
     * Actualiza una categoría existente.
     *
     * @param id            El ID de la categoría a actualizar.
     * @param categoriaDTO El DTO de la categoría con la información actualizada.
     * @return La categoría actualizada.
     * @throws Exception Si ocurre un error al actualizar la categoría.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> updateCategoria(@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody CategoriaDTO categoriaDTO) throws Exception {
        if (!id.equals(categoriaDTO.idCategoria())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CategoriaDTO updatedCategoria = categoriaService.update(categoriaDTO);
        return new ResponseEntity<>(updatedCategoria, HttpStatus.OK);
    }

    /**
     * Elimina una categoría.
     *
     * @param id El ID de la categoría a eliminar.
     * @return Una respuesta vacía con código 200 OK si la eliminación fue exitosa.
     * @throws Exception Si ocurre un error al eliminar la categoría.
     */
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) throws Exception {
        categoriaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
