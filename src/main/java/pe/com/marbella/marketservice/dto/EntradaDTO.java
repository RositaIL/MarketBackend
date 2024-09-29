package pe.com.marbella.marketservice.dto;

import java.util.List;

public record EntradaDTO(
        Long idEntrada,
        String fechaEntrada,
        Long idUsuario,
        Long idProveedor,
        boolean estado,
        List<DetalleEntradaDTO> detalleEntrada
) {
}
