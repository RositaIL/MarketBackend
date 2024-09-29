package pe.com.marbella.marketservice.dto;

public record UsuarioDTO(
        Long idUsuario,
        String nombresApellidosUsu,
        String emailUsu,
        String username,
        String password,
        Long idRol
) {
}
