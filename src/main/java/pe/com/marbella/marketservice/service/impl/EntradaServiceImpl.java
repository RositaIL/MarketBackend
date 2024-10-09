package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.dto.DetalleEntradaDTO;
import pe.com.marbella.marketservice.dto.EntradaDTO;
import pe.com.marbella.marketservice.model.DetalleEntrada;
import pe.com.marbella.marketservice.model.Entrada;
import pe.com.marbella.marketservice.model.Proveedor;
import pe.com.marbella.marketservice.model.Usuario;
import pe.com.marbella.marketservice.repository.EntradaRepository;
import pe.com.marbella.marketservice.repository.ProveedorRepository;
import pe.com.marbella.marketservice.repository.UsuarioRepository;
import pe.com.marbella.marketservice.service.DetalleEntradaService;
import pe.com.marbella.marketservice.service.EntradaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntradaServiceImpl implements EntradaService {

    @Autowired
    EntradaRepository entradaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ProveedorRepository proveedorRepository;
    @Autowired
    DetalleEntradaService detalleEntradaService;

    private EntradaDTO mapToDTO(Entrada entrada) {
        List<DetalleEntradaDTO> detalleEntradaDTOList = entrada.getDetalleEntrada().stream()
                .map(detalle -> new DetalleEntradaDTO(
                        detalle.getEntrada(),
                        detalle.getProducto(),
                        detalle.getCantidad(),
                        detalle.getPrecio()))
                .collect(Collectors.toList());

        return new EntradaDTO(
                entrada.getIdEntrada(),
                entrada.getFechaEntrada().toString(),
                entrada.getUsuario().getIdUsuario(),
                entrada.getProveedor().getIdProveedor(),
                detalleEntradaDTOList
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EntradaDTO> findAll(Pageable pageable) throws Exception {
        return entradaRepository.findByEstado(true,pageable)
                .map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public EntradaDTO findById(Long id) throws Exception {
        Entrada entrada = entradaRepository.findByIdEntradaAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada"));

        return mapToDTO(entrada);
    }

    @Override
    @Transactional
    public EntradaDTO save(EntradaDTO entradaDTO) throws Exception {
        if (entradaDTO.detalleEntrada() == null || entradaDTO.detalleEntrada().isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos un detalle de entrada.");
        }

        Usuario usuario = usuarioRepository.findById(entradaDTO.idUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Proveedor proveedor = proveedorRepository.findById(entradaDTO.idProveedor())
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));

        Entrada entrada = new Entrada(entradaDTO);
        entrada.setUsuario(usuario);
        entrada.setProveedor(proveedor);

        Entrada respuesta = entradaRepository.save(entrada);

        for (DetalleEntradaDTO detalleDTO : entradaDTO.detalleEntrada()) {
            DetalleEntrada detalleEntrada = new DetalleEntrada(detalleDTO);
            detalleEntrada.setEntrada(respuesta.getIdEntrada());
            detalleEntradaService.save(detalleEntrada);
        }

        return mapToDTO(respuesta);
    }

    @Override
    @Transactional
    public void delete(Long idEntrada) throws Exception {
        Entrada entrada = entradaRepository.findById(idEntrada)
                .orElseThrow(() -> new EntityNotFoundException("La entrada con ID: " + idEntrada + " no existe"));

        detalleEntradaService.delete(idEntrada);

        entrada.setEstado(false);
        entradaRepository.save(entrada);
    }
}
