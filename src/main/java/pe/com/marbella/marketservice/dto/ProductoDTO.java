package pe.com.marbella.marketservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

public record ProductoDTO(
        Long idPro,

        @NotEmpty(message = "-Debe ingresar el nombre del producto")
        @Size(max = 80, message = "-El nombre no puede exceder 80 caracteres")
        String nombrePro,

        @NotEmpty(message = "-Debe ingresar la descripción del producto")
        @Size(max = 255, message = "-La descripción no puede exceder 255 caracteres")
        String descripcionPro,

        @NotNull(message = "-Debe ingresar el precio")
        @Positive(message = "-El precio debe ser un número positivo")
        double precioPro,

        @NotNull(message = "-Debe ingresar el stock actual")
        @Min(value = 0, message = "-El stock debe ser un número no negativo")
        int stockActual,

        @NotNull(message = "-Debe ingresar el stock mínimo")
        @Min(value = 0, message = "-El stock debe ser un número no negativo")
        int stockMin,

        @NotNull(message = "-Debe ingresar el id de la medida")
        Long idMedida,

        @NotNull(message = "-Debe ingresar el id de la marca")
        Long idMarca,

        @NotNull(message = "-Debe ingresar el id de la categoría")
        Long idCategoria
) {
}
