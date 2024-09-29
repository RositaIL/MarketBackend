package pe.com.marbella.marketservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ProveedorDTO(
        Long idProveedor,

        @NotEmpty(message = "- Debe ingresar el nombre del proveedor")
        @Size(max = 150, message = "- El nombre del proveedor no debe exceder los 150 caracteres")
        String nombreProv,

        @NotEmpty(message = "- Debe ingresar la dirección del proveedor")
        @Size(max = 250, message = "- La dirección del proveedor no debe exceder los 250 caracteres")
        String direccProv,

        @NotEmpty(message = "- Debe ingresar el teléfono del proveedor")
        @Size(max = 15, message = "- El teléfono del proveedor no debe exceder los 15 caracteres")
        String telefProv,

        @NotEmpty(message = "- Debe ingresar el número de RUC del proveedor")
        @Size(min = 11, max = 11, message = "- El RUC debe tener 11 dígitos")
        String rucProv,

        @NotEmpty(message = "- Debe especificar el email del representante")
        @Email(message = "- Debe ser un email válido")
        @Size(max = 100, message = "- El email no debe exceder los 100 caracteres")
        String emailProv,

        @NotEmpty(message = "- Debe especificar el nombre del representante")
        @Size(max = 150, message = "- El nombre del representante no debe exceder los 150 caracteres")
        String nomRepresentante
) {
}
