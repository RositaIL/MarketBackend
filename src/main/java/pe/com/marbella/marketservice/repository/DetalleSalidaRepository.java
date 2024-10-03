package pe.com.marbella.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.marbella.marketservice.model.DetalleSalida;
import pe.com.marbella.marketservice.model.DetalleSalidaId;

import java.util.List;

@Repository
public interface DetalleSalidaRepository extends JpaRepository<DetalleSalida, DetalleSalidaId> {
    List<DetalleSalida> findDetalleSalidaBySalidaAndEstado(long idSalida, boolean estado);
}
