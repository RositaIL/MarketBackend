package pe.com.marbella.marketservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.Categoria;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByEstado(boolean estado, Pageable pageable);
    Optional<Categoria> findByIdCategoriaAndEstado(Long idCategoria, boolean estado);
    Optional<Categoria> findCategoriaByNombreCategoriaIgnoreCaseAndEstado(String nombreCategoria, boolean estado);
}
