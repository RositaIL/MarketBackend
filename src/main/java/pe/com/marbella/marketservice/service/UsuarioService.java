package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.model.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> findAll() throws Exception;
    Usuario findById(Long id) throws Exception;
    Usuario save(Usuario usuario) throws Exception;
    Usuario update(Usuario usuario) throws Exception;
    boolean delete(Long id) throws Exception;
}
