package pe.com.marbella.marketservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record MarcaDTO(
        Long idMarca,
        @NotEmpty(message = "- Debe ingresar el nombre de la marca")
        @Size(max = 30, message = "- El nombre de la marca no debe exceder los 30 caracteres")
        String nombreMarca
) {
}
