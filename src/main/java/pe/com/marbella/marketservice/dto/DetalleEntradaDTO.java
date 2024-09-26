package pe.com.marbella.marketservice.dto;

public record DetalleEntradaDTO(
        Long idEntrada,
        Long idProducto,
        int cantidad,
        double precio){
}
