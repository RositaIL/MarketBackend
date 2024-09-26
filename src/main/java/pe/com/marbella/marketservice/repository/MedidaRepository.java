package pe.com.marbella.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.Medida;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedidaRepository extends JpaRepository<Medida, Long> {
    List<Medida> findByEstado(boolean estado);
    Optional<Medida> findByIdMedidaAndEstado(Long idMedida, boolean estado);
}
