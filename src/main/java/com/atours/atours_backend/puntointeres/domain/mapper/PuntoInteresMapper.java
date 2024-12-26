package com.atours.atours_backend.puntointeres.domain.mapper;

import com.atours.atours_backend.puntointeres.domain.PuntoInteres;
import com.atours.atours_backend.puntointeres.dto.PuntoInteresDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface PuntoInteresMapper {

    PuntoInteres toEntity(PuntoInteresDTO dto);

    PuntoInteresDTO toDto(PuntoInteres puntoInteres);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(PuntoInteresDTO dto, @MappingTarget PuntoInteres entity);
}