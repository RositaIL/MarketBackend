package pe.com.marbella.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.Producto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByEstado(boolean estado);
    Optional<Producto> findByIdProAndEstado(Long idPro, boolean estado);
    @Query("SELECT p FROM Producto p WHERE p.stockActual < p.stockMin AND p.estado = :estado")
    List<Producto> findByStockActualAndEstado(boolean estado);

}
