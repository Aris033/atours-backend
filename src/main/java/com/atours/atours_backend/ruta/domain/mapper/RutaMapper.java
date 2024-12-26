package com.atours.atours_backend.ruta.domain.mapper;

import com.atours.atours_backend.ruta.domain.Ruta;
import com.atours.atours_backend.ruta.dto.RutaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RutaMapper {
    Ruta toEntity(RutaDTO dto);

    RutaDTO toDto(Ruta entity);
}
