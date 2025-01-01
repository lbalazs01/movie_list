package prog5.assignment.movielist.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.IterableMapping;
import prog5.assignment.movielist.entity.Studio;
import prog5.assignment.movielist.bean.StudioDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudioListMapper {

    @IterableMapping(elementTargetType = StudioDTO.class)
    List<StudioDTO> toDTOList(List<Studio> studios);

    @IterableMapping(elementTargetType = Studio.class)
    List<Studio> toEntityList(List<StudioDTO> studioDTOs);
}
