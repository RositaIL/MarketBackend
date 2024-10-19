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

/**
 * Implementación de la interfaz SalidaService.
 * Proporciona métodos para gestionar las salidas, incluyendo la gestión de detalles de salida.
 */
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

    /**
     * Mapea un objeto Salida a un objeto SalidaDTO.
     *
     * @param salida El objeto Salida a mapear.
     * @return El objeto SalidaDTO mapeado.
     */
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

    /**
     * Obtiene una página de salidas.
     *
     * @param pageable Objeto Pageable para la paginación.
     * @return Una página de objetos SalidaDTO.
     * @throws Exception Sí ocurre un error al obtener las salidas.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SalidaDTO> findAll(Pageable pageable) throws Exception {
        return salidaRepository.findByEstado(true, pageable)
                .map(this::mapToDTO);
    }

    /**
     * Obtiene una salida por su ID.
     *
     * @param id El ID de la salida.
     * @return El objeto SalidaDTO encontrado.
     * @throws Exception Si ocurre un error al obtener la salida.
     */
    @Override
    @Transactional(readOnly = true)
    public SalidaDTO findById(Long id) throws Exception {
        Salida salida = salidaRepository.findByIdSalidaAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Salida no encontrada"));

        return mapToDTO(salida);
    }

    /**
     * Guarda una nueva salida y sus detalles.
     *
     * @param salidaDTO El DTO de la salida a guardar.
     * @return El DTO de la salida guardada.
     * @throws Exception Si ocurre un error al guardar la salida.
     */
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

    /**
     * Elimina una salida.
     *
     * @param idSalida El ID de la salida a eliminar.
     * @throws Exception Sí ocurre un error al eliminar la salida.
     */
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
