package pe.com.marbella.marketservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.Proveedor;


import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    @Query("SELECT p FROM Proveedor p WHERE LOWER(p.nombreProv)LIKE LOWER(CONCAT('%', :nombre, '%')) AND p.estado = :estado")
    Page<Proveedor> findByNombreContainingAndEstado(@Param("nombre") String nombre, @Param("estado") boolean estado, Pageable pageable);
    Optional<Proveedor> findByIdProveedorAndEstado(Long IdProv, boolean estado);
    Optional<Proveedor> findProveedorByRucProvAndEstado(String rucProv, boolean estado);
}

