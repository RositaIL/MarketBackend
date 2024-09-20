package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pro")
    private Long idPro;

    @Column(name = "nombre_pro", nullable = false, length = 80)
    @NotEmpty(message = "-Debe ingresar el nombre del producto")
    private String nombrePro;

    @Column(name = "descripcion_pro", nullable = false, length = 255)
    @NotEmpty(message = "-Debe ingresar la descripción del producto")
    private String descripcionPro;

    @Column(name = "precio_pro", nullable = false)
    private double precioPro;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "id_cat")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_med")
    private Medida medida;

    @Column(nullable = false)
    private boolean estado;
}
