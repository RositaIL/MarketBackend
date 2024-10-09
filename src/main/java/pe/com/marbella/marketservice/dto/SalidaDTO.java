package pe.com.marbella.marketservice.dto;

import jakarta.validation.constraints.NotNull;
import pe.com.marbella.marketservice.dto.validation.OnCreate;
import pe.com.marbella.marketservice.dto.validation.OnUpdate;

import java.util.List;

public record SalidaDTO(
        @NotNull(groups = OnUpdate.class) Long idSalida,

        @NotNull(groups = OnCreate.class,message = "- La fecha de salida es obligatoria")
        String fechaSalida,

        @NotNull(groups = OnCreate.class,message = "- El ID del usuario es obligatorio")
        Long idUsuario,
        List<DetalleSalidaDTO> detalleSalida) {
}
