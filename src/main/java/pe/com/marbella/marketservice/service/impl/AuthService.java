package pe.com.marbella.marketservice.service.impl;

import org.springframework.stereotype.Service;

import pe.com.marbella.marketservice.model.Usuario;
import pe.com.marbella.marketservice.repository.UsuarioRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findUsuarioByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombreRol());

        return new User(
            usuario.getUsername(),
            usuario.getPassword(),
            usuario.isEstado(), // Usuario esta activo
            true,               // accountNonExpired
            true,               // credentialsNonExpired
            true,               // accountNonLocked
            List.of(authority)  // Rol a autoridades
        );
    }

}
