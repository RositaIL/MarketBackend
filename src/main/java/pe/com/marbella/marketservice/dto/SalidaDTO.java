package pe.com.marbella.marketservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.util.List;

public record SalidaDTO(
        Long idSalida,
        @NotNull(message = "- La fecha de salida es obligatoria")
        String fechaSalida,

        @NotNull(message = "- El ID del usuario es obligatorio")
        Long idUsuario,
        List<DetalleSalidaDTO> detalleSalida) {
}
