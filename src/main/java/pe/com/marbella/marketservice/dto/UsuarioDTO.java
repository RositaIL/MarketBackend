package pe.com.marbella.marketservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pe.com.marbella.marketservice.dto.validation.OnCreate;
import pe.com.marbella.marketservice.dto.validation.OnUpdate;

public record UsuarioDTO(
        @NotNull(groups = OnUpdate.class)Long idUsuario,

        @NotEmpty(groups = OnCreate.class,message = "- Debe especificar el nombre completo del usuario")
        @Size(groups = OnCreate.class,max = 50, message = "- El nombre completo no debe exceder los 50 caracteres")
        String nombresApellidosUsu,

        @NotEmpty(groups = OnCreate.class,message = "- Debe especificar el correo electrónico")
        @Email(groups = OnCreate.class,message = "- Debe ser un correo electrónico válido")
        @Size(groups = OnCreate.class,max = 50, message = "- El correo electrónico no debe exceder los 50 caracteres")
        String emailUsu,

        @NotEmpty(groups = OnCreate.class,message = "- Debe especificar el login de usuario")
        @Size(groups = OnCreate.class,min = 5, max = 15, message = "- El login de usuario debe medir entre 5 y 15 caracteres")
        String username,

        @NotEmpty(groups = OnCreate.class,message = "- Debe especificar la contraseña")
        String password,

        @NotNull(groups = OnCreate.class,message = "- Debe especificar el rol del usuario")
        Long idRol
) {
}
