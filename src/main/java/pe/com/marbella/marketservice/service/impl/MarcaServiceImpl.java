package pe.com.marbella.marketservice.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pe.com.marbella.marketservice.model.Marca;
import pe.com.marbella.marketservice.repository.MarcaRepository;
import pe.com.marbella.marketservice.service.MarcaService;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class MarcaServiceImpl implements MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Override
    public List<Marca> findAll() throws Exception {
        try{
            List<Marca>model = marcaRepository.findByEstado(true);
            return model;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Marca findById(Long id) throws Exception {
        try{
            Optional<Marca> opt = this.marcaRepository.findByIdMarcaAndEstado(id, true);
            return opt.get();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Marca save(Marca entity) throws Exception {
        try{
            entity = marcaRepository.save(entity);
            return entity;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Marca update(Long id, Marca entity) throws Exception {
        try{
            Optional<Marca> entityOptional = marcaRepository.findByIdMarcaAndEstado(id, true);
            Marca entityUpdate = entityOptional.get();
            entityUpdate = marcaRepository.save(entity);
            return entityUpdate;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        try{
            Optional<Marca> entityOptional = marcaRepository.findByIdMarcaAndEstado(id, true);
            Marca entityUpdate = entityOptional.get();
            if (entityOptional.isPresent()) {
                entityUpdate.eliminar();
                marcaRepository.save(entityUpdate);
                return true;
            } else {
                throw new Exception("Proveedor no encontrado");
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
