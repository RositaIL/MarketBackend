package pe.com.marbella.marketservice.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.model.Proveedor;
import pe.com.marbella.marketservice.repository.ProveedorRepository;
import pe.com.marbella.marketservice.service.ProveedorService;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorServiceImpl implements ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public List<Proveedor> findAll() throws Exception {
        try{
            return proveedorRepository.findByEstado(true);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Proveedor findById(Long id) throws Exception {
        try{
            Optional<Proveedor> opt = this.proveedorRepository.findByIdProveedorAndEstado(id, true);
            if(opt.isPresent()){
                return opt.get();
            } else {
                throw new Exception("Proveedor no encontrado");
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Proveedor save(Proveedor entity) throws Exception {
        try{
            entity = proveedorRepository.save(entity);
            return entity;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Proveedor update(Long id, Proveedor entity) throws Exception {
        try{
            return proveedorRepository.save(entity);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        try{
            Optional<Proveedor> entityOptional = proveedorRepository.findByIdProveedorAndEstado(id, true);
            if (entityOptional.isPresent()) {
                Proveedor entityUpdate = entityOptional.get();
                entityUpdate.eliminar();
                proveedorRepository.save(entityUpdate);
                return true;
            } else {
                throw new Exception("Proveedor no encontrado");
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
