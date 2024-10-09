package pe.com.marbella.marketservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.Marca;

import java.util.Optional;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    Page<Marca> findByEstado(boolean estado, Pageable pageable);
    Optional<Marca> findByIdMarcaAndEstado(Long idMarca, boolean estado);
    Optional<Marca> findMarcaByNombreMarcaIgnoreCaseAndEstado(String nombreMarca, boolean estado);
}
