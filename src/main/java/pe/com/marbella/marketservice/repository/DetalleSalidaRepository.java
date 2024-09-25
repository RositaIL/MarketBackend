package pe.com.marbella.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.DetalleSalida;

@Repository
public interface DetalleSalidaRepository extends JpaRepository<DetalleSalida, Long> {
}
