package pe.com.marbella.marketservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CategoriaDTO(
        Long idCategoria,

        @NotEmpty(message = "-Debe ingresar el nombre de la categoría")
        @Size(max = 30, message = "-El nombre de la categoría no puede exceder 30 caracteres")
        String nombreCategoria
) {
}
