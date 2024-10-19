package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.dto.MedidaDTO;
import pe.com.marbella.marketservice.model.Medida;
import pe.com.marbella.marketservice.repository.MedidaRepository;
import pe.com.marbella.marketservice.service.MedidaService;

import java.util.Optional;

/**
 * Implementación de la interfaz MedidaService.
 * Proporciona métodos para gestionar las medidas.
 */
@Service
public class MedidaServiceImpl implements MedidaService {
    @Autowired
    MedidaRepository medidaRepository;

    /**
     * Mapea un objeto Medida a un objeto MedidaDTO.
     *
     * @param medida El objeto Medida a mapear.
     * @return El objeto MedidaDTO mapeado.
     */
    private MedidaDTO mapToDTO(Medida medida) {
        return new MedidaDTO(
                medida.getIdMedida(),
                medida.getNombreMedida());
    }

    /**
     * Mapea un objeto MedidaDTO a un objeto Medida.
     *
     * @param medidaDTO El objeto MedidaDTO a mapear.
     * @return El objeto Medida mapeado.
     */
    private Medida mapToEntity(MedidaDTO medidaDTO) {
        return new Medida(medidaDTO);
    }

    /**
     * Obtiene una página de medidas que coinciden con el nombre proporcionado.
     *
     * @param nombre   El nombre a buscar.
     * @param pageable Objeto Pageable para la paginación.
     * @return Una página de objetos MedidaDTO.
     * @throws Exception Si ocurre un error al obtener las medidas.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MedidaDTO> findAll(String nombre, Pageable pageable) throws Exception {
        return medidaRepository.findByNombreContainingAndEstado(nombre,true, pageable)
                .map(this::mapToDTO);
    }

    /**
     * Obtiene una medida por su ID.
     *
     * @param id El ID de la medida.
     * @return El objeto MedidaDTO encontrado.
     * @throws Exception Si ocurre un error al obtener la medida.
     */
    @Override
    @Transactional(readOnly = true)
    public MedidaDTO findById(Long id) throws Exception {
        Medida medida = medidaRepository.findByIdMedidaAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Medida no encontrada"));
        return mapToDTO(medida);
    }

    /**
     * Guarda una nueva medida o actualiza una existente si se encuentra inactiva con el mismo nombre.
     *
     * @param medidaDTO El DTO de la medida a guardar o actualizar.
     * @return El DTO de la medida guardada o actualizada.
     * @throws Exception Si ocurre un error al guardar o actualizar la medida.
     */
    @Override
    @Transactional
    public MedidaDTO save(MedidaDTO medidaDTO) throws Exception {
        Optional<Medida> inactiveMedida = medidaRepository.findMedidaByNombreMedidaIgnoreCaseAndEstado(medidaDTO.nombreMedida(),false);

        if (inactiveMedida.isPresent()) {
            Medida medida = inactiveMedida.get();
            medida.setEstado(true);
            Medida updatedMedida = medidaRepository.save(medida);
            return mapToDTO(updatedMedida);
        }
        Medida newMedida = mapToEntity(medidaDTO);
        Medida savedMedida = medidaRepository.save(newMedida);
        return mapToDTO(savedMedida);
    }

    /**
     * Actualiza una medida existente.
     *
     * @param medidaDTO El DTO de la medida con la información actualizada.
     * @return El DTO de la medida actualizada.
     * @throws Exception Si ocurre un error al actualizar la medida.
     */
    @Override
    @Transactional
    public MedidaDTO update(MedidaDTO medidaDTO) throws Exception {
        Medida medida = medidaRepository.findById(medidaDTO.idMedida())
                .orElseThrow(() -> new EntityNotFoundException("Medida no encontrada"));
        if(medidaDTO.nombreMedida() != null && !medidaDTO.nombreMedida().trim().isEmpty()){
            medida.setNombreMedida(medidaDTO.nombreMedida());
        }
        return mapToDTO(medidaRepository.save(medida));
    }

    /**
     * Elimina una medida.
     *
     * @param id El ID de la medida a eliminar.
     * @throws Exception Si ocurre un error al eliminar la medida.
     */
    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        Medida medida = medidaRepository.findByIdMedidaAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Medida no encontrada"));
        medida.eliminar();
        medidaRepository.save(medida);
    }
}
