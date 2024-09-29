package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.model.DetalleEntrada;

public interface DetalleEntradaService {
    DetalleEntrada save(DetalleEntrada detalleEntrada) throws Exception;
    void delete(Long idDetalleEntrada) throws Exception;
}
