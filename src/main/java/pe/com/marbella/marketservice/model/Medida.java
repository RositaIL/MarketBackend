package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import lombok.*;
import pe.com.marbella.marketservice.dto.MedidaDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_medida")
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

    public void eliminar() {
        this.estado = false;
    }

    public Medida(MedidaDTO medidaDTO){
        this.idMedida=medidaDTO.idMedida();
        this.nombreMedida=medidaDTO.nombreMedida();
        this.estado=true;
    }
}
