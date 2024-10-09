package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.dto.DetalleSalidaDTO;
import pe.com.marbella.marketservice.dto.SalidaDTO;
import pe.com.marbella.marketservice.model.*;
import pe.com.marbella.marketservice.repository.ProveedorRepository;
import pe.com.marbella.marketservice.repository.SalidaRepository;
import pe.com.marbella.marketservice.repository.UsuarioRepository;
import pe.com.marbella.marketservice.service.DetalleSalidaService;
import pe.com.marbella.marketservice.service.SalidaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalidaServiceImpl implements SalidaService {
    @Autowired
    SalidaRepository salidaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ProveedorRepository proveedorRepository;
    @Autowired
    DetalleSalidaService detalleSalidaService;

    private SalidaDTO mapToDTO(Salida salida) {
        List<DetalleSalidaDTO> detalleSalidaDTOList = salida.getDetalleSalida().stream()
                .map(detalle -> new DetalleSalidaDTO(
                        detalle.getSalida(),
                        detalle.getProducto(),
                        detalle.getCantidad()))
                .collect(Collectors.toList());

        return new SalidaDTO(
                salida.getIdSalida(),
                salida.getFechaSalida().toString(),
                salida.getUsuario().getIdUsuario(),
                detalleSalidaDTOList
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalidaDTO> findAll(Pageable pageable) throws Exception {
        return salidaRepository.findByEstado(true, pageable)
                .map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public SalidaDTO findById(Long id) throws Exception {
        Salida salida = salidaRepository.findByIdSalidaAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Salida no encontrada"));

        return mapToDTO(salida);
    }

    @Override
    @Transactional
    public SalidaDTO save(SalidaDTO salidaDTO) throws Exception {
        if (salidaDTO.detalleSalida() == null || salidaDTO.detalleSalida().isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos un detalle de salida.");
        }

        Usuario usuario = usuarioRepository.findById(salidaDTO.idUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Salida salida = new Salida(salidaDTO);
        salida.setUsuario(usuario);

        Salida respuesta = salidaRepository.save(salida);

        for (DetalleSalidaDTO detalleDTO : salidaDTO.detalleSalida()) {
            DetalleSalida detalleSalida = new DetalleSalida(detalleDTO);
            detalleSalida.setSalida(respuesta.getIdSalida());
            detalleSalidaService.save(detalleSalida);
        }

        return mapToDTO(respuesta);
    }

    @Override
    @Transactional
    public void delete(Long idSalida) throws Exception {
        Salida salida = salidaRepository.findById(idSalida)
                .orElseThrow(() -> new EntityNotFoundException("La salida con ID: " + idSalida + " no existe"));

        detalleSalidaService.delete(idSalida);

        salida.setEstado(false);
        salidaRepository.save(salida);
    }
}
