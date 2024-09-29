package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.dto.CategoriaDTO;

import java.util.List;

public interface CategoriaService {

    List<CategoriaDTO> listadoCategoria() throws Exception;
    CategoriaDTO buscarCategoria(long id) throws Exception;
    CategoriaDTO save(CategoriaDTO entity) throws Exception;
    CategoriaDTO update(CategoriaDTO entity) throws Exception;
    void delete(Long id) throws Exception;
}
