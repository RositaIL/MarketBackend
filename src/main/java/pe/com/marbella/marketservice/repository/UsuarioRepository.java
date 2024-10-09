package pe.com.marbella.marketservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findUsuarioByUsername(String username);

    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nombresApellidosUsu) LIKE LOWER(CONCAT('%', :nombre, '%')) AND u.estado = :estado")
    Page<Usuario> findByNombreContainingAndEstado(@Param("nombre") String nombre, @Param("estado") boolean estado, Pageable pageable);
    
    Optional<Usuario> findByIdUsuarioAndEstado(Long idUsuario, boolean estado);
    Optional<Usuario> findUsuarioByEmailUsuIgnoreCaseAndEstado(String email, boolean estado);
}
