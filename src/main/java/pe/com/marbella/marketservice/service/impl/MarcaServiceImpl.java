package pe.com.marbella.marketservice.service.impl;

import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public List<Marca> findAll() throws Exception {
        try{
            return marcaRepository.findByEstado(true);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Marca findById(Long id) throws Exception {
        try{
            Optional<Marca> opt = marcaRepository.findByIdMarcaAndEstado(id, true);
            if(opt.isPresent()){
                return opt.get();
            } else {
            throw new Exception("Marca no encontrada");
            }
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    @Transactional
    public Marca save(Marca entity) throws Exception {
        try{
            return marcaRepository.save(entity);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    @Transactional
    public Marca update(Marca entity) throws Exception {
        try{
            return marcaRepository.save(entity);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        try{
            Optional<Marca> entityOptional = marcaRepository.findByIdMarcaAndEstado(id, true);
            if (entityOptional.isPresent()) {
                Marca entityUpdate = entityOptional.get();
                entityUpdate.eliminar();
                marcaRepository.save(entityUpdate);
                return true;
            } else {
                throw new Exception("Marca no encontrada");
            }
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
