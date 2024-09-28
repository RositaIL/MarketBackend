package pe.com.marbella.marketservice.model;
import jakarta.persistence.*;
import lombok.*;
import pe.com.marbella.marketservice.dto.DetalleEntradaDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@IdClass(DetalleEntradaId.class)
@Table(name = "detalle_entrada")
public class DetalleEntrada {

    @Id
    @Column(name = "id_entrada", nullable = false)
    private Long entrada;

    @Id
    @Column(name = "id_pro", nullable = false)
    private Long producto;

    @ManyToOne
    @JoinColumn(name = "id_entrada", nullable = false, referencedColumnName = "id_entrada")
    private Entrada entradaEntity;

    @ManyToOne
    @JoinColumn(name = "id_pro", nullable = false)
    private Producto productoEntity;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio",nullable = false)
    private double precio;

    @Column(nullable = false)
    private boolean estado;

    @PrePersist
    protected void onCreate() {
        this.estado = true;
    }

    public void eliminar() {
        this.estado = false;
    }

    public DetalleEntrada(DetalleEntradaDTO dto){
        this.entrada=dto.idEntrada();
        this.producto=dto.idProducto();
        this.cantidad=dto.cantidad();
        this.precio=dto.precio();
        this.estado=true;
    }
}
