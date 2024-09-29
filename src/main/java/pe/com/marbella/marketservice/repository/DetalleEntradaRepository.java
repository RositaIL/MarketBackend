package pe.com.marbella.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.DetalleEntrada;

import java.util.List;

@Repository
public interface DetalleEntradaRepository extends JpaRepository<DetalleEntrada, Long> {
    List<DetalleEntrada> findDetalleEntradaByEntradaAndEstado(long idEntrada,boolean estado);
}
