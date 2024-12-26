package com.atours.atours_backend.archivo.service;

import com.atours.atours_backend.archivo.domain.Archivo;
import com.atours.atours_backend.archivo.domain.mapper.ArchivoMapper;
import com.atours.atours_backend.archivo.domain.repository.ArchivoRepository;
import com.atours.atours_backend.archivo.dto.ArchivoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArchivoServiceImplTest {

    @InjectMocks
    private ArchivoServiceImpl archivoService;

    @Mock
    private ArchivoRepository archivoRepository;

    @Mock
    private ArchivoMapper archivoMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarArchivo_ShouldReturnSavedArchivoDTO() {
        ArchivoDTO archivoDTO = new ArchivoDTO();
        Archivo archivo = new Archivo();
        Archivo archivoGuardado = new Archivo();

        when(archivoMapper.toEntity(archivoDTO)).thenReturn(archivo);
        when(archivoRepository.save(archivo)).thenReturn(archivoGuardado);
        when(archivoMapper.toDto(archivoGuardado)).thenReturn(archivoDTO);

        ArchivoDTO result = archivoService.guardarArchivo(archivoDTO);

        assertNotNull(result);
        verify(archivoRepository, times(1)).save(archivo);
        verify(archivoMapper, times(1)).toEntity(archivoDTO);
        verify(archivoMapper, times(1)).toDto(archivoGuardado);
    }

    @Test
    void obtenerArchivoPorId_ShouldReturnArchivoDTO_WhenArchivoExists() {
        Long archivoId = 1L;
        Archivo archivo = new Archivo();
        ArchivoDTO archivoDTO = new ArchivoDTO();

        when(archivoRepository.findById(archivoId)).thenReturn(Optional.of(archivo));
        when(archivoMapper.toDto(archivo)).thenReturn(archivoDTO);

        ArchivoDTO result = archivoService.obtenerArchivoPorId(archivoId);

        assertNotNull(result);
        assertEquals(archivoDTO, result);
        verify(archivoRepository, times(1)).findById(archivoId);
        verify(archivoMapper, times(1)).toDto(archivo);
    }

    @Test
    void obtenerArchivoPorId_ShouldThrowException_WhenArchivoNotFound() {
        Long archivoId = 1L;

        when(archivoRepository.findById(archivoId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> archivoService.obtenerArchivoPorId(archivoId));
        verify(archivoRepository, times(1)).findById(archivoId);
        verify(archivoMapper, never()).toDto(any(Archivo.class));
    }

    @Test
    void eliminarArchivo_ShouldCallDeleteMethod() {
        Archivo archivo = new Archivo();

        archivoService.eliminarArchivo(archivo);

        verify(archivoRepository, times(1)).delete(archivo);
    }
}
