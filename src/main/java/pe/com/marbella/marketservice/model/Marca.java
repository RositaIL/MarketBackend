package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_marca", uniqueConstraints=
@UniqueConstraint(columnNames={"nombre_marca"}))
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_marca")
    private Long idMarca;

    @Column(name="nombre_marca", nullable = false, unique = true, length = 30)
    private String nombreMarca;

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
