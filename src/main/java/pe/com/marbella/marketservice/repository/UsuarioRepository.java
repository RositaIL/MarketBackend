package pe.com.marbella.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findUsuarioByUsername(String username);
    List<Usuario> findByEstado(boolean estado);
    Optional<Usuario> findByIdUsuarioAndEstado(Long idUsuario, boolean estado);
    Optional<Usuario> findUsByUsernameAndPasswordAndEstado(String username, String password, boolean estado);
}
