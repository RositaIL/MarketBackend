package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_detalle_salida")
public class DetalleSalida {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_salida")
    private Salida salida;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

}
