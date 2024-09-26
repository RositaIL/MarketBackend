package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_usuario", uniqueConstraints= {
        @UniqueConstraint(columnNames = {"nombre_apellidos_usu"}),
        @UniqueConstraint(columnNames = {"login_usu"}),
        @UniqueConstraint(columnNames = {"email_usu"})
})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usu")
    private Long idUsuario;

    @Column(name = "nombres_apellidos_usu", nullable = false, length = 50)
    @NotEmpty(message = "- Debe especificar el nombre completo del usuario")
    private String nombresApellidosUsu;

    @Column(name = "email_usu", nullable = false, length = 50, unique = true)
    @NotEmpty(message = "- Debe especificar el correo electrónico")
    private String emailUsu;

    @Column(name = "login_usu", nullable = false, length = 50, unique = true)
    @NotEmpty(message = "- Debe especificar el login de usuario")
    @Size(min = 5, max = 15, message = "- El login de usuario debe medir entre 5 y 15 caracteres")
    private String username;

    @Column(name = "contrasena_usu", nullable = false, length = 255)
    @NotEmpty(message = "- Debe especificar la contraseña")
    private String password;

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
