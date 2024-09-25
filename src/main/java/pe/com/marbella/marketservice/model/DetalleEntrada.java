package pe.com.marbella.marketservice.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_detalle_entrada")
public class DetalleEntrada {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_entrada")
    private Entrada entrada;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio",nullable = false)
    private double precio;



}
