package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import pe.com.marbella.marketservice.dto.EntradaDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "entrada_producto")
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrada")
    private Long idEntrada;

    @NotNull(message = "- La fecha de entrada es obligatoria")
    @PastOrPresent(message = "- La fecha de entrada no puede ser futura")
    @Column(name = "fecha_entrada", nullable = false)
    private LocalDate fechaEntrada;

    @ManyToOne
    @JoinColumn(name = "id_usu", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_prov", nullable = false)
    private Proveedor proveedor;

    @Column(nullable = false)
    private boolean estado;

    public Entrada(EntradaDTO dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.fechaEntrada = LocalDate.parse(dto.fechaEntrada(), formatter);
        this.usuario=dto.usuario();
        this.proveedor=dto.proveedor();
        this.estado=dto.estado();
    }
}


