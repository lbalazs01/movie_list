package prog5.assignment.movielist.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.IterableMapping;
import prog5.assignment.movielist.entity.Movie;
import prog5.assignment.movielist.bean.MovieDTO;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ActorMapper.class, StudioMapper.class})
public interface MovieListMapper {

    @IterableMapping(elementTargetType = MovieDTO.class)
    List<MovieDTO> toDTOList(List<Movie> movies);

    @IterableMapping(elementTargetType = Movie.class)
    List<Movie> toEntityList(List<MovieDTO> movieDTOs);
}
