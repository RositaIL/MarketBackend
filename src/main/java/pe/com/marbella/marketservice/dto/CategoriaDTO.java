package pe.com.marbella.marketservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pe.com.marbella.marketservice.dto.validation.OnCreate;
import pe.com.marbella.marketservice.dto.validation.OnUpdate;

public record CategoriaDTO(
        @NotNull(groups = OnUpdate.class) Long idCategoria,

        @NotEmpty(groups = OnCreate.class,message = "-Debe ingresar el nombre de la categoría")
        @Size(groups = {OnCreate.class, OnUpdate.class},max = 30, message = "-El nombre de la categoría no puede exceder 30 caracteres")
        String nombreCategoria
) {
}
