package com.atours.atours_backend.usuario.service;

import com.atours.atours_backend.ruta.domain.Ruta;
import com.atours.atours_backend.ruta.domain.repository.RutaRepository;
import com.atours.atours_backend.usuario.domain.Usuario;
import com.atours.atours_backend.usuario.domain.mapper.UsuarioMapper;
import com.atours.atours_backend.usuario.domain.repository.UsuarioRepository;
import com.atours.atours_backend.usuario.dto.UsuarioDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RutaRepository rutaRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearUsuario_ShouldReturnUsuarioDTO_WhenUserIsCreated() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        Usuario usuario = new Usuario();
        usuario.setPassword("plainPassword");
        Usuario savedUsuario = new Usuario();
        savedUsuario.setPassword("encodedPassword");
        UsuarioDTO savedUsuarioDTO = new UsuarioDTO();

        when(usuarioMapper.toEntity(usuarioDTO)).thenReturn(usuario);
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(usuarioRepository.save(usuario)).thenReturn(savedUsuario);
        when(usuarioMapper.toDto(savedUsuario)).thenReturn(savedUsuarioDTO);

        UsuarioDTO result = usuarioService.crearUsuario(usuarioDTO);

        assertNotNull(result);
        assertEquals(savedUsuarioDTO, result);
        verify(usuarioMapper, times(1)).toEntity(usuarioDTO);
        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(usuarioRepository, times(1)).save(usuario);
        verify(usuarioMapper, times(1)).toDto(savedUsuario);
    }

    @Test
    void actualizarUsuario_ShouldReturnUpdatedUsuarioDTO_WhenUserExists() {
        Long id = 1L;
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        Usuario usuario = new Usuario();
        usuario.setPassword("oldPassword");
        Usuario updatedUsuario = new Usuario();
        updatedUsuario.setPassword("encodedNewPassword");
        UsuarioDTO updatedUsuarioDTO = new UsuarioDTO();

        when(usuarioMapper.toEntity(usuarioDTO)).thenReturn(usuario);
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(usuarioRepository.save(usuario)).thenReturn(updatedUsuario);
        when(usuarioMapper.toDto(updatedUsuario)).thenReturn(updatedUsuarioDTO);

        UsuarioDTO result = usuarioService.actualizarUsuario(id, usuarioDTO);

        assertNotNull(result);
        assertEquals(updatedUsuarioDTO, result);
        verify(usuarioRepository, times(1)).findById(id);
        verify(usuarioRepository, times(1)).save(usuario);
        verify(usuarioMapper, times(1)).toDto(updatedUsuario);
    }

    @Test
    void obtenerUsuarioPorId_ShouldReturnUsuarioDTO_WhenUserExists() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toDto(usuario)).thenReturn(usuarioDTO);

        Optional<UsuarioDTO> result = usuarioService.obtenerUsuarioPorId(id);

        assertTrue(result.isPresent());
        assertEquals(usuarioDTO, result.get());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void obtenerUsuarioPorId_ShouldReturnEmpty_WhenUserNotFound() {
        Long id = 1L;

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        Optional<UsuarioDTO> result = usuarioService.obtenerUsuarioPorId(id);

        assertFalse(result.isPresent());
        verify(usuarioRepository, times(1)).findById(id);
    }


    @Test
    void actualizarUsuario_ShouldThrowException_WhenUserNotFound() {
        Long id = 1L;
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> usuarioService.actualizarUsuario(id, usuarioDTO));
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void eliminarUsuario_ShouldDeleteUser_WhenUserExists() {
        Long id = 1L;

        when(usuarioRepository.existsById(id)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(id);

        usuarioService.eliminarUsuario(id);

        verify(usuarioRepository, times(1)).existsById(id);
        verify(usuarioRepository, times(1)).deleteById(id);
    }

    @Test
    void eliminarUsuario_ShouldThrowException_WhenUserNotFound() {
        Long id = 1L;

        when(usuarioRepository.existsById(id)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> usuarioService.eliminarUsuario(id));
        verify(usuarioRepository, times(1)).existsById(id);
        verify(usuarioRepository, never()).deleteById(id);
    }

    @Test
    void addRutaFavoritaToUsuario_ShouldReturnUpdatedUsuarioDTO_WhenUserAndRouteExist() {
        Long usuarioId = 1L;
        Long rutaId = 2L;
        Usuario usuario = new Usuario();
        Ruta ruta = new Ruta();
        Usuario updatedUsuario = new Usuario();
        UsuarioDTO updatedUsuarioDTO = new UsuarioDTO();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(rutaRepository.findById(rutaId)).thenReturn(Optional.of(ruta));
        when(usuarioRepository.save(usuario)).thenReturn(updatedUsuario);
        when(usuarioMapper.toDto(updatedUsuario)).thenReturn(updatedUsuarioDTO);

        UsuarioDTO result = usuarioService.addRutaFavoritaToUsuario(usuarioId, rutaId);

        assertNotNull(result);
        assertEquals(updatedUsuarioDTO, result);
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(rutaRepository, times(1)).findById(rutaId);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void addRutaFavoritaToUsuario_ShouldThrowException_WhenUserNotFound() {
        Long usuarioId = 1L;
        Long rutaId = 2L;

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> usuarioService.addRutaFavoritaToUsuario(usuarioId, rutaId));
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(rutaRepository, never()).findById(rutaId);
    }

    @Test
    void removeRutaFavoritaFromUsuario_ShouldCallSaveMethod_WhenUserAndRouteExist() {
        Long usuarioId = 1L;
        Long rutaId = 2L;
        Usuario usuario = new Usuario();
        Ruta ruta = new Ruta();
        usuario.getRutasFavoritas().add(ruta);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(rutaRepository.findById(rutaId)).thenReturn(Optional.of(ruta));

        usuarioService.removeRutaFavoritaFromUsuario(usuarioId, rutaId);

        assertFalse(usuario.getRutasFavoritas().contains(ruta));
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(rutaRepository, times(1)).findById(rutaId);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void removeRutaFavoritaFromUsuario_ShouldThrowException_WhenUserNotFound() {
        Long usuarioId = 1L;
        Long rutaId = 2L;

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> usuarioService.removeRutaFavoritaFromUsuario(usuarioId, rutaId));
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(rutaRepository, never()).findById(rutaId);
    }
}
