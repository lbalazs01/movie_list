package prog5.assignment.movielist.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import prog5.assignment.movielist.entity.Actor;
import prog5.assignment.movielist.bean.ActorDTO;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    @Mapping(target = "id", source = "actor.id")
    @Mapping(target = "name", source = "actor.name")
    @Mapping(target = "gender", source = "actor.gender")
    @Mapping(target = "nationality", source = "actor.nationality")
    ActorDTO toDTO(Actor actor);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "gender", source = "dto.gender")
    @Mapping(target = "nationality", source = "dto.nationality")
    Actor toEntity(ActorDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "gender", source = "dto.gender")
    @Mapping(target = "nationality", source = "dto.nationality")
    void updateEntityFromDTO(ActorDTO dto, @MappingTarget Actor entity);
}
