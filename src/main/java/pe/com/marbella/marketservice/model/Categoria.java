package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import lombok.*;
import pe.com.marbella.marketservice.dto.CategoriaDTO;

@Data
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

    @PrePersist
    protected void onCreate() {
        this.estado = true;
    }

    public void eliminar() {
        this.estado = false;
    }

    public Categoria(CategoriaDTO categoriaDTO){
        this.idCategoria = categoriaDTO.idCategoria();
        this.nombreCategoria = categoriaDTO.nombreCategoria();
        this.estado = true;
    }
}
