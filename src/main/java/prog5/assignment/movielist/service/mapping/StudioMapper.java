package prog5.assignment.movielist.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import prog5.assignment.movielist.entity.Studio;
import prog5.assignment.movielist.bean.StudioDTO;

@Mapper(componentModel = "spring")
public interface StudioMapper {

    @Mapping(target = "id", source = "studio.id")
    @Mapping(target = "name", source = "studio.name")
    @Mapping(target = "location", source = "studio.location")
    StudioDTO toDTO(Studio studio);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "location", source = "dto.location")
    Studio toEntity(StudioDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "location", source = "dto.location")
    void updateEntityFromDTO(StudioDTO dto, @MappingTarget Studio entity);
}
