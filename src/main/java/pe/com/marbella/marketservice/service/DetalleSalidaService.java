package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.model.DetalleSalida;

public interface DetalleSalidaService {
    DetalleSalida save(DetalleSalida detalleSalida) throws Exception;
    void delete(Long idDetalleSalida) throws Exception;
}
