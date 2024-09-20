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
@Table(name = "unidad_medida")
public class Medida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medida")
    private  Long idMedida;

    @Column(name = "nombre_medida", nullable = false, unique = true, length = 30)
    private String nombreMedida;

    @Column(nullable = false)
    private boolean estado;

    @PrePersist
    protected void onCreate() {
        this.estado = true;
    }
}
