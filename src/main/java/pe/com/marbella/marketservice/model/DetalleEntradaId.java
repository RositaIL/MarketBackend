package pe.com.marbella.marketservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleEntradaId implements Serializable {
    private Long entrada;
    private Long producto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetalleEntradaId)) return false;
        DetalleEntradaId that = (DetalleEntradaId) o;
        return Objects.equals(entrada, that.entrada) && Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entrada, producto);
    }
}
