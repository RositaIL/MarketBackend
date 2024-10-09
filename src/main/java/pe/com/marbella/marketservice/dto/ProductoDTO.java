package pe.com.marbella.marketservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;
import pe.com.marbella.marketservice.dto.validation.OnCreate;
import pe.com.marbella.marketservice.dto.validation.OnUpdate;

public record ProductoDTO(
        @NotNull(groups = OnUpdate.class) Long idPro,

        @NotEmpty(groups = OnCreate.class,message = "-Debe ingresar el nombre del producto")
        @Size(groups = {OnCreate.class, OnUpdate.class},max = 80, message = "-El nombre no puede exceder 80 caracteres")
        String nombrePro,

        @NotEmpty(groups = OnCreate.class,message = "-Debe ingresar la descripción del producto")
        @Size(groups = {OnCreate.class, OnUpdate.class},max = 255, message = "-La descripción no puede exceder 255 caracteres")
        String descripcionPro,

        @NotNull(groups = OnCreate.class,message = "-Debe ingresar el precio")
        @Positive(groups = {OnCreate.class, OnUpdate.class},message = "-El precio debe ser un número positivo")
        double precioPro,

        @NotNull(groups = OnCreate.class,message = "-Debe ingresar el stock actual")
        @Min(groups = {OnCreate.class, OnUpdate.class},value = 0, message = "-El stock debe ser un número no negativo")
        int stockActual,

        @NotNull(groups = OnCreate.class,message = "-Debe ingresar el stock mínimo")
        @Min(groups = {OnCreate.class, OnUpdate.class},value = 0, message = "-El stock debe ser un número no negativo")
        int stockMin,

        @NotNull(groups = OnCreate.class,message = "-Debe ingresar el id de la medida")
        Long idMedida,

        @NotNull(groups = OnCreate.class,message = "-Debe ingresar el id de la marca")
        Long idMarca,

        @NotNull(groups = OnCreate.class,message = "-Debe ingresar el id de la categoría")
        Long idCategoria
) {
}
