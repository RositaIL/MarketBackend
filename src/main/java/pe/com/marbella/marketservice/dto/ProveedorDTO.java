package pe.com.marbella.marketservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pe.com.marbella.marketservice.dto.validation.OnCreate;
import pe.com.marbella.marketservice.dto.validation.OnUpdate;

public record ProveedorDTO(
        @NotNull(groups = OnUpdate.class) Long idProveedor,

        @NotEmpty(groups = OnCreate.class,message = "- Debe ingresar el nombre del proveedor")
        @Size(groups = {OnCreate.class, OnUpdate.class},max = 150, message = "- El nombre del proveedor no debe exceder los 150 caracteres")
        String nombreProv,

        @NotEmpty(groups = OnCreate.class,message = "- Debe ingresar la dirección del proveedor")
        @Size(groups = {OnCreate.class, OnUpdate.class},max = 250, message = "- La dirección del proveedor no debe exceder los 250 caracteres")
        String direccProv,

        @NotEmpty(groups = OnCreate.class,message = "- Debe ingresar el teléfono del proveedor")
        @Size(groups = {OnCreate.class, OnUpdate.class},max = 15, message = "- El teléfono del proveedor no debe exceder los 15 caracteres")
        String telefProv,

        @NotEmpty(groups = OnCreate.class,message = "- Debe ingresar el número de RUC del proveedor")
        @Size(groups = {OnCreate.class, OnUpdate.class},min = 11, max = 11, message = "- El RUC debe tener 11 dígitos")
        String rucProv,

        @NotEmpty(groups = OnCreate.class,message = "- Debe especificar el email del representante")
        @Email(groups = {OnCreate.class, OnUpdate.class},message = "- Debe ser un email válido")
        @Size(groups = {OnCreate.class, OnUpdate.class},max = 100, message = "- El email no debe exceder los 100 caracteres")
        String emailProv,

        @NotEmpty(groups = OnCreate.class,message = "- Debe especificar el nombre del representante")
        @Size(groups = {OnCreate.class, OnUpdate.class},max = 150, message = "- El nombre del representante no debe exceder los 150 caracteres")
        String nomRepresentante
) {
}
