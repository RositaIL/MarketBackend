package pe.com.marbella.marketservice.service.impl;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.model.Usuario;
import pe.com.marbella.marketservice.repository.UsuarioRepository;
import pe.com.marbella.marketservice.service.UsuarioService;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() throws Exception {
        try{
            return usuarioRepository.findByEstado(true);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findById(Long id) throws Exception {
        try{
            Optional<Usuario> opt = usuarioRepository.findByIdUsuarioAndEstado(id, true);
            if(opt.isPresent()){
                return opt.get();
            } else {
                throw new Exception("Usuario no encontrado");
            }
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    @Transactional
    public Usuario save(Usuario entity) throws Exception {
        try{
            return usuarioRepository.save(entity);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    @Transactional
    public Usuario update(Usuario entity) throws Exception {
        try{
            return usuarioRepository.save(entity);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        try{
            Optional<Usuario> entityOptional = usuarioRepository.findByIdUsuarioAndEstado(id, true);
            if (entityOptional.isPresent()) {
                Usuario entityUpdate = entityOptional.get();
                entityUpdate.eliminar();
                usuarioRepository.save(entityUpdate);
                return true;
            } else {
                throw new Exception("Usuario no encontrado");
            }
        }catch (Exception e){
            throw new Exception(e);
        }
    }
}
