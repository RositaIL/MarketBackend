package pe.com.marbella.marketservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.marbella.marketservice.model.Categoria;
import pe.com.marbella.marketservice.repository.CategoriaRepository;
import pe.com.marbella.marketservice.service.CategoriaService;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> listadoCategoria() throws Exception {
        try{
            return categoriaRepository.findAll();
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria buscarCategoria(long id) throws Exception {
        try{
            return categoriaRepository.findById(id).orElse(null);
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
