package pe.com.marbella.marketservice.service;

import org.springframework.transaction.annotation.Transactional;
import pe.com.marbella.marketservice.dto.CategoriaDTO;
import pe.com.marbella.marketservice.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    List<CategoriaDTO> listadoCategoria() throws Exception;
    CategoriaDTO buscarCategoria(long id) throws Exception;
    CategoriaDTO save(CategoriaDTO entity) throws Exception;
    CategoriaDTO update(CategoriaDTO entity) throws Exception;
    void delete(Long id) throws Exception;
}
