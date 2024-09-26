package pe.com.marbella.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.Salida;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalidaRepository extends JpaRepository<Salida, Long> {
}
