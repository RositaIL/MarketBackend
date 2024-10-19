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

/**
 * Servicio de autenticación personalizado que implementa la interfaz UserDetailsService de Spring Security.
 */
@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Carga los detalles de usuario por el nombre de usuario.
     * Este método es llamado por Spring Security durante la autenticación.
     *
     * @param username El nombre de usuario para buscar.
     * @return Un objeto UserDetails que representa al usuario autenticado.
     * @throws UsernameNotFoundException Si el usuario no se encuentra o no está activo.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findUsuarioByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre de usuario: " + username));
        //Crea una autoridad (rol) para el usuario
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombreRol());

        return new User(
            usuario.getUsername(),
            usuario.getPassword(),
            usuario.isEstado(), //Usuario esta activo
            true,               //accountNonExpired
            true,               //credentialsNonExpired
            true,               //accountNonLocked
            List.of(authority)  //Rol como autoridad
        );
    }

}
