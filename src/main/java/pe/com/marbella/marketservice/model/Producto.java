package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_producto")
@Check(constraints = "stock >= 0")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pro")
    private Long idPro;

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pro")
    @SequenceGenerator(name = "seq_pro", sequenceName = "seq_pro", initialValue = 1000, allocationSize = 1)
    @Column(name = "cod_pro", nullable = false, unique = true)
    private int codPro;

    @Column(name = "nombre_pro", nullable = false, length = 80)
    @NotEmpty(message = "-Debe ingresar el nombre del producto")
    private String nombrePro;

    @Column(name = "descripcion_pro", nullable = false, length = 255)
    @NotEmpty(message = "-Debe ingresar la descripción del producto")
    private String descripcionPro;

    @Column(name = "precio_pro", nullable = false)
    private double precioPro;

    @Column(name = "stock_actual", nullable = false)
    private double stockActual;

    @Column(name = "stock_min", nullable = false)
    private int stockMin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medida")
    private Medida medida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_marca")
    private Marca marca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cat")
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
