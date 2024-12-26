package com.atours.atours_backend.puntointeres.service;

import com.atours.atours_backend.puntointeres.domain.PuntoInteres;
import com.atours.atours_backend.puntointeres.domain.mapper.PuntoInteresMapper;
import com.atours.atours_backend.puntointeres.domain.repository.PuntoInteresRepository;
import com.atours.atours_backend.puntointeres.dto.PuntoInteresDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PuntoInteresServiceImplTest {

    @InjectMocks
    private PuntoInteresServiceImpl puntoInteresService;

    @Mock
    private PuntoInteresMapper puntoInteresMapper;

    @Mock
    private PuntoInteresRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearPunto_ShouldReturnSavedPuntoInteresDTO() {
        PuntoInteresDTO puntoInteresDTO = new PuntoInteresDTO();
        PuntoInteres punto = new PuntoInteres();
        PuntoInteres savedPunto = new PuntoInteres();
        PuntoInteresDTO savedPuntoInteresDTO = new PuntoInteresDTO();

        when(puntoInteresMapper.toEntity(puntoInteresDTO)).thenReturn(punto);
        when(repository.save(punto)).thenReturn(savedPunto);
        when(puntoInteresMapper.toDto(savedPunto)).thenReturn(savedPuntoInteresDTO);

        PuntoInteresDTO result = puntoInteresService.crearPunto(puntoInteresDTO);

        assertNotNull(result);
        assertEquals(savedPuntoInteresDTO, result);
        verify(repository, times(1)).save(punto);
        verify(puntoInteresMapper, times(1)).toEntity(puntoInteresDTO);
        verify(puntoInteresMapper, times(1)).toDto(savedPunto);
    }

    @Test
    void obtenerTodos_ShouldReturnListOfPuntoInteresDTOs() {
        List<PuntoInteres> puntosInteres = Arrays.asList(new PuntoInteres(), new PuntoInteres());
        List<PuntoInteresDTO> puntosInteresDTO = Arrays.asList(new PuntoInteresDTO(), new PuntoInteresDTO());

        when(repository.findAll()).thenReturn(puntosInteres);
        when(puntoInteresMapper.toDto(any(PuntoInteres.class))).thenReturn(new PuntoInteresDTO());

        List<PuntoInteresDTO> result = puntoInteresService.obtenerTodos();

        assertNotNull(result);
        assertEquals(puntosInteresDTO.size(), result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_ShouldReturnPuntoInteresDTO_WhenExists() {
        Long id = 1L;
        PuntoInteres punto = new PuntoInteres();
        PuntoInteresDTO puntoDTO = new PuntoInteresDTO();

        when(repository.findById(id)).thenReturn(Optional.of(punto));
        when(puntoInteresMapper.toDto(punto)).thenReturn(puntoDTO);

        PuntoInteresDTO result = puntoInteresService.obtenerPorId(id);

        assertNotNull(result);
        assertEquals(puntoDTO, result);
        verify(repository, times(1)).findById(id);
        verify(puntoInteresMapper, times(1)).toDto(punto);
    }

    @Test
    void obtenerPorId_ShouldThrowException_WhenNotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> puntoInteresService.obtenerPorId(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    void actualizarPunto_ShouldReturnUpdatedPuntoInteresDTO() {
        Long id = 1L;
        PuntoInteres punto = new PuntoInteres();
        PuntoInteresDTO puntoInteresDTO = new PuntoInteresDTO();
        PuntoInteres updatedPunto = new PuntoInteres();
        PuntoInteresDTO updatedPuntoInteresDTO = new PuntoInteresDTO();

        when(repository.findById(id)).thenReturn(Optional.of(punto));
        doNothing().when(puntoInteresMapper).updateEntityFromDto(puntoInteresDTO, punto);
        when(repository.save(punto)).thenReturn(updatedPunto);
        when(puntoInteresMapper.toDto(updatedPunto)).thenReturn(updatedPuntoInteresDTO);

        PuntoInteresDTO result = puntoInteresService.actualizarPunto(id, puntoInteresDTO);

        assertNotNull(result);
        assertEquals(updatedPuntoInteresDTO, result);
        verify(repository, times(1)).save(punto);
    }

    @Test
    void eliminarPunto_ShouldCallDelete_WhenIdExists() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);

        puntoInteresService.eliminarPunto(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void eliminarPunto_ShouldThrowException_WhenIdNotExists() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> puntoInteresService.eliminarPunto(id));
        verify(repository, times(1)).existsById(id);
    }
}
