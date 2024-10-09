package pe.com.marbella.marketservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.Medida;

import java.util.Optional;

@Repository
public interface MedidaRepository extends JpaRepository<Medida, Long> {
    Page<Medida> findByEstado(boolean estado, Pageable pageable);
    Optional<Medida> findByIdMedidaAndEstado(Long idMedida, boolean estado);
    Optional<Medida> findMedidaByNombreMedidaIgnoreCaseAndEstado(String nombreMedida, boolean estado);
}
