package pe.com.marbella.marketservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.Categoria;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query("SELECT c FROM Categoria c WHERE LOWER(c.nombreCategoria) LIKE LOWER(CONCAT('%', :nombre, '%')) AND c.estado = :estado")
    Page<Categoria> findByNombreContainingAndEstado(@Param("nombre") String nombre, @Param("estado") boolean estado, Pageable pageable);
    Optional<Categoria> findByIdCategoriaAndEstado(Long idCategoria, boolean estado);
    Optional<Categoria> findCategoriaByNombreCategoriaIgnoreCaseAndEstado(String nombreCategoria, boolean estado);
}
