package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.dto.UsuarioDTO;
import pe.com.marbella.marketservice.model.Rol;
import pe.com.marbella.marketservice.model.Usuario;
import pe.com.marbella.marketservice.repository.RolRepository;
import pe.com.marbella.marketservice.repository.UsuarioRepository;
import pe.com.marbella.marketservice.service.UsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    RolRepository rolRepository;

    private UsuarioDTO mapToDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getIdUsuario(),
                usuario.getNombresApellidosUsu(),
                usuario.getEmailUsu(),
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getRol().getIdRol()
        );
    }

    private Usuario mapToEntity(UsuarioDTO usuarioDTO, Rol rol) {
        return new Usuario(
                usuarioDTO.idUsuario(),
                usuarioDTO.nombresApellidosUsu(),
                usuarioDTO.emailUsu(),
                usuarioDTO.username(),
                usuarioDTO.password(),
                rol,
                true);
    }

    private Rol getRole(Long id){
        return rolRepository.findByIdRolAndEstado(id,true).orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() throws Exception {
        return usuarioRepository.findByEstado(true).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO findById(Long id) throws Exception {
        Usuario usuario = usuarioRepository.findByIdUsuarioAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return mapToDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioDTO save(UsuarioDTO usuarioDTO) throws Exception {
        Rol rol = getRole(usuarioDTO.idRol());
        Usuario newUsuario = mapToEntity(usuarioDTO,rol);
        Usuario savedUsuario = usuarioRepository.save(newUsuario);
        return mapToDTO(savedUsuario);
    }

    @Override
    @Transactional
    public UsuarioDTO update(UsuarioDTO usuarioDTO) throws Exception {
        Usuario usuario = usuarioRepository.findById(usuarioDTO.idUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        usuario.setNombresApellidosUsu(usuarioDTO.nombresApellidosUsu());
        usuario.setEmailUsu(usuarioDTO.emailUsu());
        usuario.setUsername(usuarioDTO.username());
        usuario.setPassword(usuarioDTO.password());
        Rol rol = getRole(usuarioDTO.idRol());
        usuario.setRol(rol);
        return mapToDTO(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        Usuario usuario = usuarioRepository.findByIdUsuarioAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        usuario.eliminar();
        usuarioRepository.save(usuario);
    }
}
