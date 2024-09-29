package pe.com.marbella.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.marbella.marketservice.model.Rol;

import java.util.List;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol,Long> {
    List<Rol> findByEstado(boolean estado);
    Optional<Rol> findByIdRolAndEstado(Long idRol, boolean estado);
}
