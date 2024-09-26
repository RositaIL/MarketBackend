package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import lombok.*;
import pe.com.marbella.marketservice.dto.SalidaDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "salida_producto")
public class Salida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_salida")
    private Long idSalida;

    @Column(name = "fecha_salida")
    private LocalDate fechaSalida;

    @ManyToOne
    @JoinColumn(name = "id_usu")
    private Usuario idUsuario;

    @Column(nullable = false)
    private boolean estado;


    public Salida(SalidaDTO dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.fechaSalida = LocalDate.parse(dto.fechaSalida(), formatter);
        this.idUsuario=dto.usuario();
        this.estado=dto.estado();
    }
}
