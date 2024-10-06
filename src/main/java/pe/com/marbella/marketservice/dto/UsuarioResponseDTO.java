package pe.com.marbella.marketservice.dto;

public record UsuarioResponseDTO(
        Long idUsuario,
        String nombresApellidosUsu,
        String emailUsu,
        String username,
        Long idRol
) {
}
