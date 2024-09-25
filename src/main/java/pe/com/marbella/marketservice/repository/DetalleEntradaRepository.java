package pe.com.marbella.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.DetalleEntrada;

@Repository
public interface DetalleEntradaRepository extends JpaRepository<DetalleEntrada, Long> {
}
