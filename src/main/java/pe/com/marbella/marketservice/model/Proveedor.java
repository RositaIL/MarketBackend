package pe.com.marbella.marketservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.com.marbella.marketservice.dto.ProveedorDTO;

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
    private String nombreProv;

    @Column(name = "direcc_prov", nullable = false, length = 250)
    private String direccProv;

    @Column(name = "telef_prov", nullable = false, length = 15)
    private String telefProv;

    @Column(name = "ruc_prov", nullable = false, length = 11, unique = true)
    private String rucProv;

    @Column(name = "email_prov", nullable = false, length = 100)
    private String emailProv;

    @Column(name = "nom_representante", nullable = false, length = 150)
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

    public Proveedor(ProveedorDTO proveedorDTO){
        this.idProveedor = proveedorDTO.idProveedor();
        this.nombreProv = proveedorDTO.nombreProv();
        this.direccProv = proveedorDTO.direccProv();
        this.telefProv = proveedorDTO.telefProv();
        this.rucProv = proveedorDTO.rucProv();
        this.emailProv = proveedorDTO.emailProv();
        this.nomRepresentante = proveedorDTO.nomRepresentante();
        this.estado = true;
    }
}
