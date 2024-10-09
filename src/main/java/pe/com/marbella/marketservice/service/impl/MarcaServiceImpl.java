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

@Service
@Validated
public class MarcaServiceImpl implements MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    private MarcaDTO mapToDTO(Marca marca) {
        return new MarcaDTO(
                marca.getIdMarca(),
                marca.getNombreMarca()
        );
    }

    private Marca mapToEntity(MarcaDTO marcaDTO) {
        return new Marca(marcaDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MarcaDTO> findAll(String nombre, Pageable pageable) throws Exception {
        return marcaRepository.findByNombreContainingAndEstado(nombre, true, pageable)
                .map(this::mapToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public MarcaDTO findById(Long id) throws Exception {
        Marca marca = marcaRepository.findByIdMarcaAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada"));
        return mapToDTO(marca);
    }

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

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        Marca marca = marcaRepository.findByIdMarcaAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada"));
        marca.eliminar();
        marcaRepository.save(marca);
    }
}
