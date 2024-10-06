package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_usuario", uniqueConstraints= {
        @UniqueConstraint(columnNames = {"login_usu"}),
        @UniqueConstraint(columnNames = {"email_usu"})
})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usu")
    private Long idUsuario;

    @Column(name = "nombres_apellidos_usu", nullable = false, length = 50)
    private String nombresApellidosUsu;

    @Column(name = "email_usu", nullable = false, length = 50, unique = true)
    private String emailUsu;

    @Column(name = "login_usu", nullable = false, length = 50, unique = true)
    private String username;

    @Column(name = "contrasena_usu", nullable = false, length = 255)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @Column(nullable = false)
    private boolean estado;

    @PrePersist
    protected void onCreate() {
        this.estado = true;
    }

    public void eliminar() {
        this.estado = false;
    }

}
