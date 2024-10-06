package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.dto.UsuarioDTO;
import pe.com.marbella.marketservice.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {
    List<UsuarioResponseDTO> findAll() throws Exception;
    UsuarioResponseDTO findById(Long id) throws Exception;
    UsuarioResponseDTO save(UsuarioDTO usuario) throws Exception;
    UsuarioResponseDTO update(UsuarioDTO usuario) throws Exception;
    void delete(Long id) throws Exception;
}
