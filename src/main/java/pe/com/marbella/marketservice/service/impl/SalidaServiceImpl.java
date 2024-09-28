package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.dto.DetalleSalidaDTO;
import pe.com.marbella.marketservice.dto.SalidaDTO;
import pe.com.marbella.marketservice.model.*;
import pe.com.marbella.marketservice.repository.ProveedorRepository;
import pe.com.marbella.marketservice.repository.SalidaRepository;
import pe.com.marbella.marketservice.repository.UsuarioRepository;
import pe.com.marbella.marketservice.service.DetalleSalidaService;
import pe.com.marbella.marketservice.service.SalidaService;

import java.util.List;
import java.util.Optional;

@Service
public class SalidaServiceImpl implements SalidaService {
    @Autowired
    SalidaRepository salidaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ProveedorRepository proveedorRepository;
    @Autowired
    DetalleSalidaService detalleSalidaService;

    @Override
    @Transactional(readOnly = true)
    public List<Salida> findAll() throws Exception {
        try{
            return salidaRepository.findByEstado(true);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Salida findById(Long id) throws Exception {
        try{
            Optional<Salida> opt = salidaRepository.findByIdSalidaAndEstado(id, true);
            if(opt.isPresent()){
                return opt.get();
            } else {
                throw new Exception("Salida no encontrada");
            }
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    @Transactional
    public Salida save(SalidaDTO entity) throws Exception {
        try {
            Optional<Usuario> usuario = usuarioRepository.findById(entity.usuario().getIdUsuario());

            if (usuario.isEmpty()) {
                throw new Exception("Usuario no encontrado");
            }

            Salida salida = new Salida(entity);
            Salida respuesta = salidaRepository.save(salida);

            for (DetalleSalidaDTO detalleDTO : entity.detalleSalida()) {
                DetalleSalida detalleSalida = new DetalleSalida(detalleDTO);
                detalleSalidaService.save(detalleSalida);
            }

            return respuesta;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    @Transactional
    public void delete(Long idSalida) throws Exception {
        try {
            Salida salida = salidaRepository.findById(idSalida)
                    .orElseThrow(() -> new EntityNotFoundException("La salida con ID: " + idSalida + " no existe"));
            detalleSalidaService.delete(idSalida);
            salida.setEstado(false);
            salidaRepository.save(salida);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
