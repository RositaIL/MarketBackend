package pe.com.marbella.marketservice.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.model.Entrada;
import pe.com.marbella.marketservice.model.Marca;
import pe.com.marbella.marketservice.repository.EntradaRepository;
import pe.com.marbella.marketservice.service.EntradaService;

import java.util.List;
import java.util.Optional;

@Service
public class EntradaServiceImpl implements EntradaService {

    @Autowired
    EntradaRepository entradaRepository;

    @Override
    public List<Entrada> findAll() throws Exception {
        try{
            return entradaRepository.findByEstado(true);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Entrada findById(Long id) throws Exception {
        try{
            Optional<Entrada> opt = entradaRepository.findByIdEntradaAndEstado(id, true);
            if(opt.isPresent()){
                return opt.get();
            } else {
                throw new Exception("Entrada no encontrada");
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
