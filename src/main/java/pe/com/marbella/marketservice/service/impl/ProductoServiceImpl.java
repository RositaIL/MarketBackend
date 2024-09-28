package pe.com.marbella.marketservice.service.impl;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.model.Producto;
import pe.com.marbella.marketservice.repository.ProductoRepository;
import pe.com.marbella.marketservice.service.ProductoService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() throws Exception {
        try{
            return productoRepository.findByEstado(true);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) throws Exception {
        try{
            Optional<Producto> opt = productoRepository.findByIdProAndEstado(id, true);
            if(opt.isPresent()){
                return opt.get();
            } else {
                throw new Exception("Producto no encontrado");
            }
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    @Transactional
    public Producto save(Producto entity) throws Exception {
        try{
            return productoRepository.save(entity);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    @Transactional
    public Producto update(Producto entity) throws Exception {
        try{
            return productoRepository.save(entity);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        try{
            Optional<Producto> entityOptional = productoRepository.findByIdProAndEstado(id, true);
            if (entityOptional.isPresent()) {
                Producto entityUpdate = entityOptional.get();
                entityUpdate.eliminar();
                productoRepository.save(entityUpdate);
                return true;
            } else {
                throw new Exception("Producto no encontrado");
            }
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
