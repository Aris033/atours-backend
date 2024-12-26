package com.atours.atours_backend.usuario.controller;

import com.atours.atours_backend.usuario.dto.UsuarioDTO;
import com.atours.atours_backend.usuario.service.IUsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private IUsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearUsuario_ShouldReturnCreatedUsuarioDTO() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        UsuarioDTO createdUsuarioDTO = new UsuarioDTO();

        when(usuarioService.crearUsuario(usuarioDTO)).thenReturn(createdUsuarioDTO);

        ResponseEntity<UsuarioDTO> response = usuarioController.crearUsuario(usuarioDTO);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(createdUsuarioDTO, response.getBody());
        verify(usuarioService, times(1)).crearUsuario(usuarioDTO);
    }

    @Test
    void obtenerUsuarioPorId_ShouldReturnUsuarioDTO_WhenUserExists() {
        Long id = 1L;
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        when(usuarioService.obtenerUsuarioPorId(id)).thenReturn(Optional.of(usuarioDTO));

        ResponseEntity<UsuarioDTO> response = usuarioController.obtenerUsuarioPorId(id);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(usuarioDTO, response.getBody());
        verify(usuarioService, times(1)).obtenerUsuarioPorId(id);
    }

    @Test
    void obtenerUsuarioPorId_ShouldReturnNotFound_WhenUserNotExists() {
        Long id = 1L;

        when(usuarioService.obtenerUsuarioPorId(id)).thenReturn(Optional.empty());

        ResponseEntity<UsuarioDTO> response = usuarioController.obtenerUsuarioPorId(id);

        assertNotNull(response);
        assertEquals(NOT_FOUND, response.getStatusCode());
        verify(usuarioService, times(1)).obtenerUsuarioPorId(id);
    }

    @Test
    void actualizarUsuario_ShouldReturnUpdatedUsuarioDTO() {
        Long id = 1L;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        UsuarioDTO updatedUsuarioDTO = new UsuarioDTO();

        when(usuarioService.actualizarUsuario(id, usuarioDTO)).thenReturn(updatedUsuarioDTO);

        ResponseEntity<UsuarioDTO> response = usuarioController.actualizarUsuario(id, usuarioDTO);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(updatedUsuarioDTO, response.getBody());
        verify(usuarioService, times(1)).actualizarUsuario(id, usuarioDTO);
    }

    @Test
    void eliminarUsuario_ShouldReturnNoContent_WhenUserDeleted() {
        Long id = 1L;

        doNothing().when(usuarioService).eliminarUsuario(id);

        ResponseEntity<Void> response = usuarioController.eliminarUsuario(id);

        assertNotNull(response);
        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(usuarioService, times(1)).eliminarUsuario(id);
    }

    @Test
    void addRutaFavoritaToUsuario_ShouldReturnUpdatedUsuarioDTO() {
        Long id = 1L;
        Long rutaId = 2L;
        UsuarioDTO updatedUsuarioDTO = new UsuarioDTO();

        when(usuarioService.addRutaFavoritaToUsuario(id, rutaId)).thenReturn(updatedUsuarioDTO);

        ResponseEntity<UsuarioDTO> response = usuarioController.addRutaFavoritaToUsuario(id, rutaId);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(updatedUsuarioDTO, response.getBody());
        verify(usuarioService, times(1)).addRutaFavoritaToUsuario(id, rutaId);
    }

    @Test
    void removeRutaFavoritaFromUsuario_ShouldReturnNoContent_WhenRouteRemoved() {
        Long id = 1L;
        Long rutaId = 2L;

        doNothing().when(usuarioService).removeRutaFavoritaFromUsuario(id, rutaId);

        ResponseEntity<Void> response = usuarioController.removeRutaFavoritaFromUsuario(id, rutaId);

        assertNotNull(response);
        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(usuarioService, times(1)).removeRutaFavoritaFromUsuario(id, rutaId);
    }
}
