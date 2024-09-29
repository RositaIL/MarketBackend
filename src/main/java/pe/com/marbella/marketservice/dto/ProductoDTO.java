package pe.com.marbella.marketservice.dto;

public record ProductoDTO(
        Long idPro,
        String nombrePro,
        String descripcionPro,
        double precioPro,
        int stockActual,
        int stockMin,
        Long idMedida,
        Long idMarca,
        Long idCategoria
) {
}
