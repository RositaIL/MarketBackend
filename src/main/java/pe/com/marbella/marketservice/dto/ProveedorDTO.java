package pe.com.marbella.marketservice.dto;

public record ProveedorDTO(
        Long idProveedor,
        String nombreProv,
        String direccProv,
        String telefProv,
        String rucProv,
        String emailProv,
        String nomRepresentante
) {
}
