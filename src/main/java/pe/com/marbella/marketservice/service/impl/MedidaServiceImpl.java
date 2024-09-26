package pe.com.marbella.marketservice.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.model.Medida;
import pe.com.marbella.marketservice.repository.MedidaRepository;
import pe.com.marbella.marketservice.service.MedidaService;

import java.util.List;
import java.util.Optional;

@Service
public class MedidaServiceImpl implements MedidaService {
    @Autowired
    MedidaRepository medidaRepository;

    @Override
    public List<Medida> findAll() throws Exception {
        try{
            return medidaRepository.findByEstado(true);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Medida findById(Long id) throws Exception {
        try{
            Optional<Medida> opt = this.medidaRepository.findByIdMedidaAndEstado(id, true);
            if(opt.isPresent()){
                return opt.get();
            } else {
                throw new Exception("Medida no encontrada");
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Medida save(Medida entity) throws Exception {
        try{
            entity = medidaRepository.save(entity);
            return entity;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Medida update(Long id, Medida entity) throws Exception {
        try{
            return medidaRepository.save(entity);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        try{
            Optional<Medida> entityOptional = medidaRepository.findByIdMedidaAndEstado(id, true);
            if (entityOptional.isPresent()) {
                Medida entityUpdate = entityOptional.get();
                entityUpdate.eliminar();
                medidaRepository.save(entityUpdate);
                return true;
            } else {
                throw new Exception("Medida no encontrada");
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
