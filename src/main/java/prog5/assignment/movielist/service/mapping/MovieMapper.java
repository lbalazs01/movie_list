package prog5.assignment.movielist.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import prog5.assignment.movielist.bean.MovieDTO;
import prog5.assignment.movielist.entity.Movie;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "leadActorId", source = "leadActorId")
    @Mapping(target = "studioId", source = "studioId")
    Movie toEntity(MovieDTO dto);

    @Mapping(target = "leadActorId", source = "leadActorId")
    @Mapping(target = "studioId", source = "studioId")
    MovieDTO toDTO(Movie entity);

    void updateEntityFromDTO(MovieDTO dto, @MappingTarget Movie entity);
}

