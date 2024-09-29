package pe.com.marbella.marketservice.service;

import pe.com.marbella.marketservice.dto.RolDTO;

import java.util.List;

public interface RolService {
    List<RolDTO> listadoRoles() throws Exception;
    RolDTO buscarRol(Long id) throws Exception;
}
