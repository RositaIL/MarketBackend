package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_proveedor")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prov")
    private Long idProveedor;

    @Column(name = "nombre_prov", nullable = false, length = 150)
    @NotEmpty(message = "- Debe ingresar el nombre del proveedor")
    private String nombreProv;

    @Column(name = "direcc_prov", nullable = false, length = 250)
    @NotEmpty(message = "- Debe ingresar la dirección del proveedor")
    private String direccProv;

    @Column(name = "telef_prov", nullable = false, length = 15)
    @NotEmpty(message = "- Debe ingresar el telefono del proveedor")
    private String telefProv;

    @Column(name = "ruc_prov", nullable = false, length = 8, unique = true)
    @NotEmpty(message = "- Debe ingresar el nro de ruc del proveedor")
    @Size(min = 11, max = 11, message = "- El RUC debe tener 11 dígitos")
    private String rucProv;

    @Column(name = "email_prov", nullable = false, length = 100)
    @NotEmpty(message = "- Debe especificar el email del representante")
    private String emailProv;

    @Column(name = "nom_representante", nullable = false, length = 150)
    @NotEmpty(message = "- Debe especificar el nombre del representante")
    private String nomRepresentante;

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
