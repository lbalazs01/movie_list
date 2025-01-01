package prog5.assignment.movielist.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.IterableMapping;
import prog5.assignment.movielist.entity.Actor;
import prog5.assignment.movielist.bean.ActorDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActorListMapper {

    @IterableMapping(elementTargetType = ActorDTO.class)
    List<ActorDTO> toDTOList(List<Actor> actors);

    @IterableMapping(elementTargetType = Actor.class)
    List<Actor> toEntityList(List<ActorDTO> actorDTOs);
}
