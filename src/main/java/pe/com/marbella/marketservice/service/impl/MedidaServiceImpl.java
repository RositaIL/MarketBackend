package pe.com.marbella.marketservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.marbella.marketservice.dto.MedidaDTO;
import pe.com.marbella.marketservice.model.Medida;
import pe.com.marbella.marketservice.repository.MedidaRepository;
import pe.com.marbella.marketservice.service.MedidaService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedidaServiceImpl implements MedidaService {
    @Autowired
    MedidaRepository medidaRepository;

    private MedidaDTO mapToDTO(Medida medida) {
        return new MedidaDTO(
                medida.getIdMedida(),
                medida.getNombreMedida());
    }

    private Medida mapToEntity(MedidaDTO medidaDTO) {
        return new Medida(medidaDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedidaDTO> findAll(Pageable pageable) throws Exception {
        return medidaRepository.findByEstado(true, pageable).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MedidaDTO findById(Long id) throws Exception {
        Medida medida = medidaRepository.findByIdMedidaAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Medida no encontrada"));
        return mapToDTO(medida);
    }

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

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        Medida medida = medidaRepository.findByIdMedidaAndEstado(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Medida no encontrada"));
        medida.eliminar();
        medidaRepository.save(medida);
    }
}
