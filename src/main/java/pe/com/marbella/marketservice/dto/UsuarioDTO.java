package pe.com.marbella.marketservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioDTO(
        Long idUsuario,

        @NotEmpty(message = "- Debe especificar el nombre completo del usuario")
        @Size(max = 50, message = "- El nombre completo no debe exceder los 50 caracteres")
        String nombresApellidosUsu,

        @NotEmpty(message = "- Debe especificar el correo electrónico")
        @Email(message = "- Debe ser un correo electrónico válido")
        @Size(max = 50, message = "- El correo electrónico no debe exceder los 50 caracteres")
        String emailUsu,

        @NotEmpty(message = "- Debe especificar el login de usuario")
        @Size(min = 5, max = 15, message = "- El login de usuario debe medir entre 5 y 15 caracteres")
        String username,

        @NotEmpty(message = "- Debe especificar la contraseña")
        String password,

        @NotNull(message = "- Debe especificar el rol del usuario")
        Long idRol
) {
}
