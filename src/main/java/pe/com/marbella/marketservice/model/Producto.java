package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @SequenceGenerator(name = "seq_pro", sequenceName = "seq_pro", initialValue = 1000, allocationSize = 1)
    @Column(name="id_pro")
    private Long idPro;

    @Column(name = "nombre_pro", nullable = false, length = 80)
    @NotEmpty(message = "-Debe ingresar el nombre del producto")
    private String nombrePro;

    @Column(name = "descripcion_pro", nullable = false, length = 255)
    @NotEmpty(message = "-Debe ingresar la descripción del producto")
    private String descripcionPro;

    @Column(name = "precio_pro", nullable = false)
    @NotNull(message = "- Debe ingresar el precio")
    @Positive(message = "- El precio debe ser un número positivo")
    private double precioPro;

    @Column(name = "stock_actual", nullable = false)
    @NotNull(message = "- Debe ingresar el stock actual del producto")
    @Min(value = 0, message = "- El stock debe ser un número no negativo")
    private int stockActual;

    @Column(name = "stock_min", nullable = false)
    @NotNull(message = "- Debe ingresar el stock mínimo del producto")
    @Min(value = 0, message = "- El stock debe ser un número no negativo")
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
