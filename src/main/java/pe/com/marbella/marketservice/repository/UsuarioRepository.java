package pe.com.marbella.marketservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findUsuarioByUsername(String username);
    Page<Usuario> findByEstado(boolean estado, Pageable pageable);
    Optional<Usuario> findByIdUsuarioAndEstado(Long idUsuario, boolean estado);
    Optional<Usuario> findUsuarioByEmailUsuIgnoreCaseAndEstado(String email, boolean estado);
}
