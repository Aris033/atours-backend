package com.atours.atours_backend.usuario.domain.mapper;

import com.atours.atours_backend.usuario.domain.Usuario;
import com.atours.atours_backend.usuario.dto.UsuarioDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO toDto(Usuario usuario);

    Usuario toEntity(UsuarioDTO usuarioDTO);
}
