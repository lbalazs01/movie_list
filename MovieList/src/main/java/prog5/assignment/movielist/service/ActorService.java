package prog5.assignment.movielist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prog5.assignment.movielist.entity.Actor;
import prog5.assignment.movielist.bean.ActorDTO;
import prog5.assignment.movielist.repository.ActorRepository;
import prog5.assignment.movielist.service.mapping.ActorListMapper;
import prog5.assignment.movielist.service.mapping.ActorMapper;
import prog5.assignment.movielist.exception.custom.EntityNotFoundException;
import prog5.assignment.movielist.exception.custom.BadRequestException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;
    private final ActorListMapper actorListMapper;

    @Autowired
    public ActorService(ActorRepository actorRepository, ActorMapper actorMapper, ActorListMapper actorListMapper) {
        this.actorRepository = actorRepository;
        this.actorMapper = actorMapper;
        this.actorListMapper = actorListMapper;
    }

    public ActorDTO saveActor(ActorDTO actorDTO) {
        if (actorDTO.getName() == null || actorDTO.getName().isEmpty()) {
            throw new BadRequestException("Actor name cannot be null or empty");
        }

        Actor actor = actorMapper.toEntity(actorDTO);
        actor = actorRepository.save(actor);
        return actorMapper.toDTO(actor);
    }

    public ActorDTO getActorById(Long id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actor not found with id: " + id));
        return actorMapper.toDTO(actor);
    }

    public List<ActorDTO> getAllActors() {
        Iterable<Actor> actorsIterable = actorRepository.findAll();
        List<Actor> actors = StreamSupport.stream(actorsIterable.spliterator(), false)
                .collect(Collectors.toList());
        return actorListMapper.toDTOList(actors);
    }

    public ActorDTO updateActor(Long id, ActorDTO actorDTO) {
        if (actorDTO.getName() == null || actorDTO.getName().isEmpty()) {
            throw new BadRequestException("Actor name cannot be null or empty");
        }

        if (!actorRepository.existsById(id)) {
            throw new EntityNotFoundException("Actor not found with id: " + id);
        }

        actorDTO.setId(id);
        Actor actor = actorMapper.toEntity(actorDTO);
        actor = actorRepository.save(actor);
        return actorMapper.toDTO(actor);
    }

    public void deleteActor(Long id) {
        if (!actorRepository.existsById(id)) {
            throw new EntityNotFoundException("Actor not found with id: " + id);
        }
        actorRepository.deleteById(id);
    }
}
