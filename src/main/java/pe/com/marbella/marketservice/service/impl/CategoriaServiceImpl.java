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

@Service
@Validated
public class CategoriaServiceImpl implements CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    private CategoriaDTO mapToDTO(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getIdCategoria(),
                categoria.getNombreCategoria()
        );
    }

    private Categoria mapToEntity(CategoriaDTO categoriaDTO) {
        return new Categoria(categoriaDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaDTO> listadoCategoria(String nombre, Pageable pageable) throws Exception{
        return categoriaRepository.findByNombreContainingAndEstado(nombre, true, pageable)
                .map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDTO buscarCategoria(long id) throws Exception{
        Categoria categoria = categoriaRepository.findByIdCategoriaAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
        return mapToDTO(categoria);
    }

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

    @Override
    @Transactional
    public void delete(Long id) throws Exception{
        Categoria categoria = categoriaRepository.findByIdCategoriaAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
        categoria.eliminar();
        categoriaRepository.save(categoria);
    }
}
