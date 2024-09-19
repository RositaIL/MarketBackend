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
@Table(name = "tb_categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cat")
    private Long idCategoria;

    @Column(name = "nombre_cat", nullable = false, unique = true, length = 30)
    private String nombreCategoria;

    @Column(nullable = false)
    private boolean estado;

}
