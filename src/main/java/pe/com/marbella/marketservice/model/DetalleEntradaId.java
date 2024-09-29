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
}
