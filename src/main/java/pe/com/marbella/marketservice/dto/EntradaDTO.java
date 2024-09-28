package pe.com.marbella.marketservice.dto;

import pe.com.marbella.marketservice.model.Proveedor;
import pe.com.marbella.marketservice.model.Usuario;

import java.util.List;

public record EntradaDTO(
        Long idEntrada,
        String fechaEntrada,
        Usuario usuario,
        Proveedor proveedor,
        boolean estado,
        List<DetalleEntradaDTO> detalleEntrada
) {
}
