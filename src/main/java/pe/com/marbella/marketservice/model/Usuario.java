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
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre_usuario", nullable = false, length = 50)
    private String nombreUsu;

    @Column(name = "email_usuario", nullable = false, length = 50, unique = true)
    private String emailUsu;

    @Column(name = "login_usuario", nullable = false, length = 50, unique = true)
    private String loginUsu;

    @Column(name = "contrasena_usuario", nullable = false, length = 255)
    private String contrasenaUsu;

    @ManyToOne(fetch = FetchType.LAZY)
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
