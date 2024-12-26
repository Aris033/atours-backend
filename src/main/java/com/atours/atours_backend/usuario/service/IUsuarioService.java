package com.atours.atours_backend.usuario.service;

import com.atours.atours_backend.usuario.dto.UsuarioDTO;

import java.util.Optional;

public interface IUsuarioService {
    UsuarioDTO crearUsuario(UsuarioDTO usuarioRequestDTO);

    Optional<UsuarioDTO> obtenerUsuarioPorId(Long id);

    Optional<UsuarioDTO> obtenerUsuarioPorEmail(String email);

    UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioActualizado);

    void eliminarUsuario(Long id);

    UsuarioDTO addRutaFavoritaToUsuario(Long usuarioId, Long rutaId);

    void removeRutaFavoritaFromUsuario(Long usuarioId, Long rutaId);
}
