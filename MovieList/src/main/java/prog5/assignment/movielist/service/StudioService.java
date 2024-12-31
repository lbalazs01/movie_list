package prog5.assignment.movielist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prog5.assignment.movielist.entity.Studio;
import prog5.assignment.movielist.bean.StudioDTO;
import prog5.assignment.movielist.repository.StudioRepository;
import prog5.assignment.movielist.service.mapping.StudioListMapper;
import prog5.assignment.movielist.service.mapping.StudioMapper;
import prog5.assignment.movielist.exception.custom.EntityNotFoundException;
import prog5.assignment.movielist.exception.custom.BadRequestException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudioService {

    private final StudioRepository studioRepository;
    private final StudioMapper studioMapper;
    private final StudioListMapper studioListMapper;

    @Autowired
    public StudioService(StudioRepository studioRepository, StudioMapper studioMapper, StudioListMapper studioListMapper) {
        this.studioRepository = studioRepository;
        this.studioMapper = studioMapper;
        this.studioListMapper = studioListMapper;
    }

    public StudioDTO saveStudio(StudioDTO studioDTO) {
        if (studioDTO.getName() == null || studioDTO.getName().isEmpty()) {
            throw new BadRequestException("Studio name cannot be null or empty");
        }

        Studio studio = studioMapper.toEntity(studioDTO);
        studio = studioRepository.save(studio);
        return studioMapper.toDTO(studio);
    }

    public StudioDTO getStudioById(Long id) {
        Studio studio = studioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Studio not found with id: " + id));
        return studioMapper.toDTO(studio);
    }

    public List<StudioDTO> getAllStudios() {
        Iterable<Studio> studiosIterable = studioRepository.findAll();
        List<Studio> studios = StreamSupport.stream(studiosIterable.spliterator(), false)
                .collect(Collectors.toList());
        return studioListMapper.toDTOList(studios);
    }

    public StudioDTO updateStudio(Long id, StudioDTO studioDTO) {
        if (studioDTO.getName() == null || studioDTO.getName().isEmpty()) {
            throw new BadRequestException("Studio name cannot be null or empty");
        }

        if (!studioRepository.existsById(id)) {
            throw new EntityNotFoundException("Studio not found with id: " + id);
        }

        studioDTO.setId(id);
        Studio studio = studioMapper.toEntity(studioDTO);
        studio = studioRepository.save(studio);
        return studioMapper.toDTO(studio);
    }

    public void deleteStudio(Long id) {
        if (!studioRepository.existsById(id)) {
            throw new EntityNotFoundException("Studio not found with id: " + id);
        }
        studioRepository.deleteById(id);
    }
}
