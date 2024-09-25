package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    List<Categoria> findByEstado(boolean estado);
    Optional<Categoria> findByIdCategoriaAndEstado(Long idCategoria, boolean estado);

}
