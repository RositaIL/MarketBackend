package pe.com.marbella.marketservice.model;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@IdClass(DetalleEntradaId.class)
@Table(name = "detalle_entrada")
public class DetalleEntrada {

    @Id
    @Column(name = "id_entrada", nullable = false)
    private Long entrada;

    @Id
    @Column(name = "id_pro", nullable = false)
    private Long producto;

    @ManyToOne
    @JoinColumn(name = "id_entrada", nullable = false)
    private Entrada entradaEntity;

    @ManyToOne
    @JoinColumn(name = "id_pro", nullable = false)
    private Producto productoEntity;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio",nullable = false)
    private double precio;

}
