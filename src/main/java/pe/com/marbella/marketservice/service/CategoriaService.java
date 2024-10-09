package pe.com.marbella.marketservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.marbella.marketservice.dto.CategoriaDTO;

public interface CategoriaService {

    Page<CategoriaDTO> listadoCategoria(Pageable pageable) throws Exception;
    CategoriaDTO buscarCategoria(long id) throws Exception;
    CategoriaDTO save(CategoriaDTO entity) throws Exception;
    CategoriaDTO update(CategoriaDTO entity) throws Exception;
    void delete(Long id) throws Exception;
}
