package pe.com.marbella.marketservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.Marca;

import java.util.Optional;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    @Query("SELECT m FROM Marca m WHERE LOWER(m.nombreMarca) LIKE LOWER(CONCAT('%', :nombre, '%')) AND m.estado = :estado")
    Page<Marca> findByNombreContainingAndEstado(@Param("nombre") String nombre, @Param("estado") boolean estado, Pageable pageable);
    Optional<Marca> findByIdMarcaAndEstado(Long idMarca, boolean estado);
    Optional<Marca> findMarcaByNombreMarcaIgnoreCaseAndEstado(String nombreMarca, boolean estado);
}
