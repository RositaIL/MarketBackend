package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pe.com.marbella.marketservice.dto.MarcaDTO;
import pe.com.marbella.marketservice.model.Marca;
import pe.com.marbella.marketservice.repository.MarcaRepository;
import pe.com.marbella.marketservice.service.MarcaService;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<MarcaDTO> findAll() throws Exception {
        return marcaRepository.findByEstado(true).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
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
        Marca newMarca = mapToEntity(marcaDTO);
        Marca savedMarca = marcaRepository.save(newMarca);
        return mapToDTO(savedMarca);
    }

    @Override
    @Transactional
    public MarcaDTO update(MarcaDTO marcaDTO) throws Exception {
        Marca marca = marcaRepository.findById(marcaDTO.idMarca())
                .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada"));
        marca.setNombreMarca(marcaDTO.nombreMarca());
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
