package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(DetalleSalida.class)
@Table(name = "detalle_salida")
public class DetalleSalida {

    @Id
    @ManyToOne(targetEntity = Salida.class)
    @JoinColumn(name = "id_salida", nullable = false)
    private Salida salida;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_pro", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

}
