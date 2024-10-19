package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pe.com.marbella.marketservice.dto.MarcaDTO;
import pe.com.marbella.marketservice.model.Marca;
import pe.com.marbella.marketservice.repository.MarcaRepository;
import pe.com.marbella.marketservice.service.MarcaService;

import java.util.Optional;

/**
 * Implementación de la interfaz MarcaService.
 * Proporciona métodos para gestionar las marcas.
 */
@Service
@Validated
public class MarcaServiceImpl implements MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    /**
     * Mapea un objeto Marca a un objeto MarcaDTO.
     *
     * @param marca El objeto Marca a mapear.
     * @return El objeto MarcaDTO mapeado.
     */
    private MarcaDTO mapToDTO(Marca marca) {
        return new MarcaDTO(
                marca.getIdMarca(),
                marca.getNombreMarca()
        );
    }

    /**
     * Mapea un objeto MarcaDTO a un objeto Marca.
     *
     * @param marcaDTO El objeto MarcaDTO a mapear.
     * @return El objeto Marca mapeado.
     */
    private Marca mapToEntity(MarcaDTO marcaDTO) {
        return new Marca(marcaDTO);
    }

    /**
     * Obtiene una página de marcas que coinciden con el nombre proporcionado.
     *
     * @param nombre   El nombre a buscar.
     * @param pageable Objeto Pageable para la paginación.
     * @return Una página de objetos MarcaDTO.
     * @throws Exception Si ocurre un error al obtener las marcas.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MarcaDTO> findAll(String nombre, Pageable pageable) throws Exception {
        return marcaRepository.findByNombreContainingAndEstado(nombre, true, pageable)
                .map(this::mapToDTO);
    }

    /**
     * Obtiene una marca por su ID.
     *
     * @param id El ID de la marca.
     * @return El objeto MarcaDTO encontrado.
     * @throws Exception Si ocurre un error al obtener la marca.
     */
    @Override
    @Transactional(readOnly = true)
    public MarcaDTO findById(Long id) throws Exception {
        Marca marca = marcaRepository.findByIdMarcaAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada"));
        return mapToDTO(marca);
    }

    /**
     * Guarda una nueva marca o actualiza una existente si se encuentra inactiva con el mismo nombre.
     *
     * @param marcaDTO El DTO de la marca a guardar o actualizar.
     * @return El DTO de la marca guardada o actualizada.
     * @throws Exception Si ocurre un error al guardar o actualizar la marca.
     */
    @Override
    @Transactional
    public MarcaDTO save(MarcaDTO marcaDTO) throws Exception {
        Optional<Marca> inactiveMarca = marcaRepository.findMarcaByNombreMarcaIgnoreCaseAndEstado(marcaDTO.nombreMarca(),false);

        if (inactiveMarca.isPresent()) {
            Marca marca = inactiveMarca.get();
            marca.setEstado(true);
            Marca updatedMarca = marcaRepository.save(marca);
            return mapToDTO(updatedMarca);
        }
        Marca newMarca = mapToEntity(marcaDTO);
        Marca savedMarca = marcaRepository.save(newMarca);
        return mapToDTO(savedMarca);
    }

    /**
     * Actualiza una marca existente.
     *
     * @param marcaDTO El DTO de la marca con la información actualizada.
     * @return El DTO de la marca actualizada.
     * @throws Exception Si ocurre un error al actualizar la marca.
     */
    @Override
    @Transactional
    public MarcaDTO update(MarcaDTO marcaDTO) throws Exception {
        Marca marca = marcaRepository.findById(marcaDTO.idMarca())
                .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada"));
        if(marcaDTO.nombreMarca() != null && !marcaDTO.nombreMarca().trim().isEmpty()){
            marca.setNombreMarca(marcaDTO.nombreMarca());
        }
        return mapToDTO(marcaRepository.save(marca));
    }

    /**
     * Elimina una marca.
     *
     * @param id El ID de la marca a eliminar.
     * @throws Exception Si ocurre un error al eliminar la marca.
     */
    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        Marca marca = marcaRepository.findByIdMarcaAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada"));
        marca.eliminar();
        marcaRepository.save(marca);
    }
}
