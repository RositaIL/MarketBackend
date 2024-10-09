package pe.com.marbella.marketservice.dto;

import jakarta.validation.constraints.NotNull;
import pe.com.marbella.marketservice.dto.validation.OnCreate;
import pe.com.marbella.marketservice.dto.validation.OnUpdate;

import java.util.List;

public record EntradaDTO(
        @NotNull(groups = OnUpdate.class) Long idEntrada,

        @NotNull(groups = OnCreate.class,message = "- La fecha de entrada es obligatoria")
        String fechaEntrada,

        @NotNull(groups = OnCreate.class,message = "- El ID del usuario es obligatorio")
        Long idUsuario,

        @NotNull(groups = OnCreate.class,message = "- El ID del proveedor es obligatorio")
        Long idProveedor,
        List<DetalleEntradaDTO> detalleEntrada
) {
}
