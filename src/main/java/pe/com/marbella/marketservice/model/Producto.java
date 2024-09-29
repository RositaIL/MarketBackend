package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_producto")
@Check(constraints = "stock_actual >= 0 AND stock_min >= 0")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pro")
    @SequenceGenerator(name = "seq_pro", sequenceName = "seq_pro", initialValue = 1010, allocationSize = 1)
    @Column(name="id_pro")
    private Long idPro;

    @Column(name = "nombre_pro", nullable = false, length = 80)
    private String nombrePro;

    @Column(name = "descripcion_pro", nullable = false, length = 255)
    private String descripcionPro;

    @Column(name = "precio_pro", nullable = false)
    private double precioPro;

    @Column(name = "stock_actual", nullable = false)
    private int stockActual;

    @Column(name = "stock_min", nullable = false)
    private int stockMin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medida", nullable = false)
    private Medida medida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_marca", nullable = false)
    private Marca marca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cat", nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private boolean estado;

    @PrePersist
    public void onCreate() {

        this.estado = true;
    }

    public void eliminar() {

        this.estado = false;
    }
}
