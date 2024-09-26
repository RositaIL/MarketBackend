package pe.com.marbella.marketservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleSalidaId implements Serializable {
    private Long salida;
    private Long producto;
}
