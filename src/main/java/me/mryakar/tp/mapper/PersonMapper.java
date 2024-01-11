package me.mryakar.tp.mapper;

import me.mryakar.tp.dto.PersonDto;
import me.mryakar.tp.entity.PersonEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface PersonMapper {

    PersonEntity toEntity(PersonDto personDto);

    PersonDto toDto(PersonEntity personEntity);

    List<PersonDto> toDto(List<PersonEntity> personEntities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePartially(PersonDto personDto, @MappingTarget PersonEntity personEntity);
}