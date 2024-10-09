package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.dto.UsuarioDTO;
import pe.com.marbella.marketservice.dto.UsuarioResponseDTO;
import pe.com.marbella.marketservice.model.Rol;
import pe.com.marbella.marketservice.model.Usuario;
import pe.com.marbella.marketservice.repository.RolRepository;
import pe.com.marbella.marketservice.repository.UsuarioRepository;
import pe.com.marbella.marketservice.service.UsuarioService;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    RolRepository rolRepository;
    @Autowired
    private PasswordEncoder codificador;

    private UsuarioResponseDTO mapToDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNombresApellidosUsu(),
                usuario.getEmailUsu(),
                usuario.getUsername(),
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
    public Page<UsuarioResponseDTO> findAll(String nombre, Pageable pageable) throws Exception {
        return usuarioRepository.findByNombreContainingAndEstado(nombre, true, pageable)
                            .map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO findById(Long id) throws Exception {
        Usuario usuario = usuarioRepository.findByIdUsuarioAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return mapToDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO save(UsuarioDTO usuarioDTO) throws Exception {
        Optional<Usuario> usuarioInactivoOpt = usuarioRepository.findUsuarioByEmailUsuIgnoreCaseAndEstado(usuarioDTO.emailUsu(), false);
        Rol rol = getRole(usuarioDTO.idRol());
        if (usuarioInactivoOpt.isPresent()) {
            Usuario usuarioInactivo = usuarioInactivoOpt.get();
            usuarioInactivo.setNombresApellidosUsu(usuarioDTO.nombresApellidosUsu());
            usuarioInactivo.setEmailUsu(usuarioDTO.emailUsu());
            usuarioInactivo.setUsername(usuarioDTO.username());
            usuarioInactivo.setPassword(codificador.encode(usuarioDTO.password()));
            usuarioInactivo.setRol(rol);
            usuarioInactivo.setEstado(true);

            Usuario updatedUsuario = usuarioRepository.save(usuarioInactivo);
            return mapToDTO(updatedUsuario);
        } else {
            Usuario newUsuario = mapToEntity(usuarioDTO, rol);
            newUsuario.setPassword(codificador.encode(usuarioDTO.password()));
            Usuario savedUsuario = usuarioRepository.save(newUsuario);
            return mapToDTO(savedUsuario);
        }
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(UsuarioDTO usuarioDTO) throws Exception {
        Usuario usuario = usuarioRepository.findById(usuarioDTO.idUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        if (usuarioDTO.nombresApellidosUsu() != null && !usuarioDTO.nombresApellidosUsu().trim().isEmpty()) {
            usuario.setNombresApellidosUsu(usuarioDTO.nombresApellidosUsu());
        }
        if (usuarioDTO.emailUsu() != null && !usuarioDTO.emailUsu().trim().isEmpty()) {
            usuario.setEmailUsu(usuarioDTO.emailUsu());
        }
        if (usuarioDTO.username() != null && !usuarioDTO.username().trim().isEmpty()) {
            usuario.setUsername(usuarioDTO.username());
        }
        if (usuarioDTO.password() != null && !usuarioDTO.password().trim().isEmpty()) {
            usuario.setPassword(codificador.encode(usuarioDTO.password()));
        }
        if (usuarioDTO.idRol() != null) {
            Rol rol = getRole(usuarioDTO.idRol());
            usuario.setRol(rol);
        }
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
