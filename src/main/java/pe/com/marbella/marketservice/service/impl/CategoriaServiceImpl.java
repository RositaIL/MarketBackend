package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pe.com.marbella.marketservice.dto.CategoriaDTO;
import pe.com.marbella.marketservice.model.Categoria;
import pe.com.marbella.marketservice.repository.CategoriaRepository;
import pe.com.marbella.marketservice.service.CategoriaService;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<CategoriaDTO> listadoCategoria() throws Exception{
        return categoriaRepository.findByEstado(true).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
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
        Categoria newCategoria = mapToEntity(categoriaDTO);
        Categoria respuesta = categoriaRepository.save(newCategoria);
        return mapToDTO(respuesta);
    }

    @Override
    @Transactional
    public CategoriaDTO update(CategoriaDTO categoriaDTO) throws Exception{
        Categoria categoria = categoriaRepository.findById(categoriaDTO.idCategoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
        categoria.setNombreCategoria(categoriaDTO.nombreCategoria());
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
