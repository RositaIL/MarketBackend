package pe.com.marbella.marketservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.Producto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Page<Producto> findByEstado(boolean estado, Pageable pageable);
    Optional<Producto> findByIdProAndEstado(Long idPro, boolean estado);
    @Query("SELECT p FROM Producto p WHERE p.stockActual < p.stockMin AND p.estado = :estado")
    List<Producto> findByStockActualAndEstado(boolean estado);
    Page<Producto> findAllByCategoria_IdCategoriaAndEstado(Long idCategoria, boolean estado, Pageable pageable);
}
