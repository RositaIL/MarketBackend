package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import lombok.*;
import pe.com.marbella.marketservice.dto.EntradaDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    @PrePersist
    protected void onCreate() {
        this.estado = true;
    }

    public void eliminar() {
        this.estado = false;
    }

    @OneToMany(mappedBy = "entradaEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleEntrada> detalleEntrada=new ArrayList<>();

    public Entrada(EntradaDTO dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.fechaEntrada = LocalDate.parse(dto.fechaEntrada(), formatter);
        this.estado=true;
    }
}


