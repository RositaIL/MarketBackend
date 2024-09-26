package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.model.Entrada;

import java.util.List;

public interface EntradaService {
    public List<Entrada> findAll() throws Exception;
    public Entrada findById(Long id) throws Exception;
}
