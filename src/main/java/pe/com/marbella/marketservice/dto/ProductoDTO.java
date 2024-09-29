package pe.com.marbella.marketservice.dto;

public record ProductoDTO(
        String nombrePro,
        String descripcionPro,
        double precioPro,
        int stockActual,
        int stockMin,
        int idMedida,
        int idMarca,
        int idCategoria
) {
}
