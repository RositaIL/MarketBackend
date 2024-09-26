package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(DetalleSalidaId.class)
@Table(name = "detalle_salida")
public class DetalleSalida {

    @Id
    @Column(name = "id_salida", nullable = false)
    private Long salida;

    @Id
    @Column(name = "id_pro", nullable = false)
    private Long producto;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_salida", referencedColumnName = "id_salida", insertable = false, updatable = false, nullable = false)
    private Salida salidaEntity;

    @ManyToOne
    @JoinColumn(name = "id_pro", referencedColumnName = "id_pro", insertable = false, updatable = false, nullable = false)
    private Producto productoEntity;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private boolean estado;

    @PrePersist
    protected void onCreate() {
        this.estado = true;
    }

    public void eliminar() {
        this.estado = false;
    }

}
