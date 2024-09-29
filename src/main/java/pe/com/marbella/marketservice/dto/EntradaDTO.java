package pe.com.marbella.marketservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.util.List;

public record EntradaDTO(
        Long idEntrada,

        @NotNull(message = "- La fecha de entrada es obligatoria")
        String fechaEntrada,

        @NotNull(message = "- El ID del usuario es obligatorio")
        Long idUsuario,

        @NotNull(message = "- El ID del proveedor es obligatorio")
        Long idProveedor,
        List<DetalleEntradaDTO> detalleEntrada
) {
}
