package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.dto.DetalleEntradaDTO;
import pe.com.marbella.marketservice.dto.EntradaDTO;
import pe.com.marbella.marketservice.model.DetalleEntrada;
import pe.com.marbella.marketservice.model.Entrada;
import pe.com.marbella.marketservice.model.Proveedor;
import pe.com.marbella.marketservice.model.Usuario;
import pe.com.marbella.marketservice.repository.EntradaRepository;
import pe.com.marbella.marketservice.repository.ProveedorRepository;
import pe.com.marbella.marketservice.repository.UsuarioRepository;
import pe.com.marbella.marketservice.service.DetalleEntradaService;
import pe.com.marbella.marketservice.service.EntradaService;

import java.util.List;
import java.util.Optional;

@Service
public class EntradaServiceImpl implements EntradaService {

    @Autowired
    EntradaRepository entradaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ProveedorRepository proveedorRepository;
    @Autowired
    DetalleEntradaService detalleEntradaService;

    @Override
    @Transactional(readOnly = true)
    public List<Entrada> findAll() throws Exception {
        try{
            return entradaRepository.findByEstado(true);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Entrada findById(Long id) throws Exception {
        try{
            Optional<Entrada> opt = entradaRepository.findByIdEntradaAndEstado(id, true);
            if(opt.isPresent()){
                return opt.get();
            } else {
                throw new Exception("Entrada no encontrada");
            }
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    @Transactional
    public Entrada save(EntradaDTO entity) throws Exception {
        try {
            Optional<Usuario> usuario = usuarioRepository.findById(entity.usuario().getIdUsuario());
            Optional<Proveedor> proveedor = proveedorRepository.findById(entity.proveedor().getIdProveedor());

            if (usuario.isEmpty() || proveedor.isEmpty()) {
                throw new Exception("Usuario o proveedor no encontrado");
            }

            Entrada entrada = new Entrada(entity);
            Entrada respuesta = entradaRepository.save(entrada);

            for (DetalleEntradaDTO detalleDTO : entity.detalleEntrada()) {
                DetalleEntrada detalleEntrada = new DetalleEntrada(detalleDTO);
                detalleEntradaService.save(detalleEntrada);
            }

            return respuesta;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    @Transactional
    public void delete(Long idEntrada) throws Exception {
        try {
            Entrada entrada = entradaRepository.findById(idEntrada)
                    .orElseThrow(() -> new EntityNotFoundException("La entrada con ID: " + idEntrada + " no existe"));
            detalleEntradaService.delete(idEntrada);
            entrada.setEstado(false);
            entradaRepository.save(entrada);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
