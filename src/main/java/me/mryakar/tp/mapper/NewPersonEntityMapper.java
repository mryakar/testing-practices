package me.mryakar.tp.mapper;

import me.mryakar.tp.dto.NewPersonDto;
import me.mryakar.tp.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewPersonEntityMapper {
    PersonEntity toEntity(NewPersonDto newPersonDto);
}