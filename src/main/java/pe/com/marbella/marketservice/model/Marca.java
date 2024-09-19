package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "marca", uniqueConstraints=
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
}
