package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    List<Categoria> listadoCategoria() throws Exception;
    Categoria buscarCategoria(long id) throws Exception;
}
