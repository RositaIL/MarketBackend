package pe.com.marbella.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
