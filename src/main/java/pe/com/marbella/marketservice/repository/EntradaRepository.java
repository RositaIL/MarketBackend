package pe.com.marbella.marketservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.Entrada;

import java.util.Optional;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    Page<Entrada> findByEstado(boolean estado, Pageable pageable);
    Optional<Entrada> findByIdEntradaAndEstado(Long idEntrada, boolean estado);
}
