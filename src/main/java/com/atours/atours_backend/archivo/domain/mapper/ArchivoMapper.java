package com.atours.atours_backend.archivo.domain.mapper;

import com.atours.atours_backend.archivo.domain.Archivo;
import com.atours.atours_backend.archivo.dto.ArchivoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArchivoMapper {

    ArchivoDTO toDto(Archivo archivo);

    Archivo toEntity(ArchivoDTO archivoDTO);
}
