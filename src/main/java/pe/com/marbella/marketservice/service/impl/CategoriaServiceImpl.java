package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pe.com.marbella.marketservice.dto.CategoriaDTO;
import pe.com.marbella.marketservice.model.Categoria;
import pe.com.marbella.marketservice.repository.CategoriaRepository;
import pe.com.marbella.marketservice.service.CategoriaService;

import java.util.Optional;

/**
 * Implementación de la interfaz CategoriaService.
 * Proporciona métodos para gestionar las categorías.
 */
@Service
@Validated
public class CategoriaServiceImpl implements CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * Mapea un objeto Categoria a un objeto CategoriaDTO.
     *
     * @param categoria El objeto Categoria a mapear.
     * @return El objeto CategoriaDTO mapeado.
     */
    private CategoriaDTO mapToDTO(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getIdCategoria(),
                categoria.getNombreCategoria()
        );
    }

    /**
     * Mapea un objeto CategoriaDTO a un objeto Categoria.
     *
     * @param categoriaDTO El objeto CategoriaDTO a mapear.
     * @return El objeto Categoria mapeado.
     */
    private Categoria mapToEntity(CategoriaDTO categoriaDTO) {
        return new Categoria(categoriaDTO);
    }

    /**
     * Obtiene una página de categorías que coinciden con el nombre proporcionado.
     *
     * @param nombre   El nombre a buscar.
     * @param pageable Objeto Pageable para la paginación.
     * @return Una página de objetos CategoriaDTO.
     * @throws Exception Si ocurre un error al obtener las categorías.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaDTO> listadoCategoria(String nombre, Pageable pageable) throws Exception{
        return categoriaRepository.findByNombreContainingAndEstado(nombre, true, pageable)
                .map(this::mapToDTO);
    }

    /**
     * Obtiene una categoría por su ID.
     *
     * @param id El ID de la categoría.
     * @return El objeto CategoriaDTO encontrado.
     * @throws Exception Si ocurre un error al obtener la categoría.
     */
    @Override
    @Transactional(readOnly = true)
    public CategoriaDTO buscarCategoria(long id) throws Exception{
        Categoria categoria = categoriaRepository.findByIdCategoriaAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
        return mapToDTO(categoria);
    }

    /**
     * Guarda una nueva categoría o actualiza una existente si se encuentra inactiva con el mismo nombre.
     *
     * @param categoriaDTO El DTO de la categoría a guardar o actualizar.
     * @return El DTO de la categoría guardada o actualizada.
     * @throws Exception Si ocurre un error al guardar o actualizar la categoría.
     */
    @Override
    @Transactional
    public CategoriaDTO save(CategoriaDTO categoriaDTO) throws Exception{
        Optional<Categoria> inactiveCategoria = categoriaRepository.findCategoriaByNombreCategoriaIgnoreCaseAndEstado(categoriaDTO.nombreCategoria(),false);
        if (inactiveCategoria.isPresent()) {
            Categoria categoria = inactiveCategoria.get();
            categoria.setEstado(true);
            Categoria updatedCategoria = categoriaRepository.save(categoria);
            return mapToDTO(updatedCategoria);
        }
        Categoria newCategoria = mapToEntity(categoriaDTO);
        Categoria respuesta = categoriaRepository.save(newCategoria);
        return mapToDTO(respuesta);
    }

    /**
     * Actualiza una categoría existente.
     *
     * @param categoriaDTO El DTO de la categoría con la información actualizada.
     * @return El DTO de la categoría actualizada.
     * @throws Exception Si ocurre un error al actualizar la categoría.
     */
    @Override
    @Transactional
    public CategoriaDTO update(CategoriaDTO categoriaDTO) throws Exception{
        Categoria categoria = categoriaRepository.findById(categoriaDTO.idCategoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
        if(categoriaDTO.nombreCategoria() != null && !categoriaDTO.nombreCategoria().trim().isEmpty()){
            categoria.setNombreCategoria(categoriaDTO.nombreCategoria());
        }
        return mapToDTO(categoriaRepository.save(categoria));
    }

    /**
     * Elimina una categoría.
     *
     * @param id El ID de la categoría a eliminar.
     * @throws Exception Si ocurre un error al eliminar la categoría.
     */
    @Override
    @Transactional
    public void delete(Long id) throws Exception{
        Categoria categoria = categoriaRepository.findByIdCategoriaAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
        categoria.eliminar();
        categoriaRepository.save(categoria);
    }
}
