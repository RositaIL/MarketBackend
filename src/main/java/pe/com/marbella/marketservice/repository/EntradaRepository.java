package pe.com.marbella.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.marbella.marketservice.model.Entrada;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
}
