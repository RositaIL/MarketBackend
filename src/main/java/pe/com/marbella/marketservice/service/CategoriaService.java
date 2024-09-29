package pe.com.marbella.marketservice.service;

import org.springframework.transaction.annotation.Transactional;
import pe.com.marbella.marketservice.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    List<Categoria> listadoCategoria() throws Exception;
    Categoria buscarCategoria(long id) throws Exception;

    @Transactional
    Categoria save(Categoria entity) throws Exception;

    @Transactional
    Categoria update(Categoria entity) throws Exception;

    @Transactional
    boolean delete(Long id) throws Exception;
}
