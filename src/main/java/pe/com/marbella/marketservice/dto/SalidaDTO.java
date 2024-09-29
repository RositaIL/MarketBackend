package pe.com.marbella.marketservice.dto;

import pe.com.marbella.marketservice.model.Usuario;

import java.util.List;

public record SalidaDTO(
        Long idSalida,
        String fechaSalida,
        Long idUsuario,
        boolean estado,
        List<DetalleSalidaDTO> detalleSalida) {
}
