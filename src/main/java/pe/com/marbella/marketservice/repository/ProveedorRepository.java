package pe.com.marbella.marketservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.Proveedor;


import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    List<Proveedor> findByEstado(boolean estado, Pageable pageable);
    Optional<Proveedor> findByIdProveedorAndEstado(Long IdProv, boolean estado);
}

