package pe.com.marbella.marketservice.service;

import org.springframework.transaction.annotation.Transactional;
import pe.com.marbella.marketservice.model.DetalleSalida;

public interface DetalleSalidaService {
    DetalleSalida save(DetalleSalida detalleSalida) throws Exception;
    void delete(Long idDetalleSalida) throws Exception;
}
