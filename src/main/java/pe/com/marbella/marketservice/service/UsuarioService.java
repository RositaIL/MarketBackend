package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> findAll() throws Exception;
    UsuarioDTO findById(Long id) throws Exception;
    UsuarioDTO save(UsuarioDTO usuario) throws Exception;
    UsuarioDTO update(UsuarioDTO usuario) throws Exception;
    void delete(Long id) throws Exception;
}
