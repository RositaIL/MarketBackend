package pe.com.marbella.marketservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record MedidaDTO(
        Long idMedida,

        @NotEmpty(message = "- Debe ingresar el nombre de la medida")
        @Size(max = 30, message = "- El nombre de la medida no debe exceder los 30 caracteres")
        String nombreMedida
) {
}
