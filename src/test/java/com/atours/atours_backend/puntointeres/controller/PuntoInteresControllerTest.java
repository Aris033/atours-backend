package com.atours.atours_backend.puntointeres.controller;

import com.atours.atours_backend.archivo.service.ArchivoServiceImpl;
import com.atours.atours_backend.puntointeres.dto.PuntoInteresDTO;
import com.atours.atours_backend.puntointeres.service.IPuntoInteresService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

class PuntoInteresControllerTest {

    @InjectMocks
    private PuntoInteresController puntoInteresController;

    @Mock
    private IPuntoInteresService puntoInteresService;

    @Mock
    private ArchivoServiceImpl archivoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearPunto_ShouldReturnCreatedPuntoInteresDTO() {
        PuntoInteresDTO puntoInteresDTO = new PuntoInteresDTO();
        PuntoInteresDTO createdPuntoInteresDTO = new PuntoInteresDTO();

        when(puntoInteresService.crearPunto(puntoInteresDTO)).thenReturn(createdPuntoInteresDTO);

        ResponseEntity<PuntoInteresDTO> response = puntoInteresController.crearPunto(puntoInteresDTO);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(createdPuntoInteresDTO, response.getBody());
        verify(puntoInteresService, times(1)).crearPunto(puntoInteresDTO);
    }

    @Test
    void obtenerTodos_ShouldReturnListOfPuntoInteresDTOs() {
        List<PuntoInteresDTO> puntosInteres = Arrays.asList(new PuntoInteresDTO(), new PuntoInteresDTO());

        when(puntoInteresService.obtenerTodos()).thenReturn(puntosInteres);

        ResponseEntity<List<PuntoInteresDTO>> response = puntoInteresController.obtenerTodos();

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        verify(puntoInteresService, times(1)).obtenerTodos();
    }

    @Test
    void obtenerPorId_ShouldReturnPuntoInteresDTO_WhenExists() {
        Long id = 1L;
        PuntoInteresDTO puntoInteresDTO = new PuntoInteresDTO();

        when(puntoInteresService.obtenerPorId(id)).thenReturn(puntoInteresDTO);

        ResponseEntity<PuntoInteresDTO> response = puntoInteresController.obtenerPorId(id);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(puntoInteresDTO, response.getBody());
        verify(puntoInteresService, times(1)).obtenerPorId(id);
    }

    @Test
    void actualizarPunto_ShouldReturnUpdatedPuntoInteresDTO() {
        Long id = 1L;
        PuntoInteresDTO puntoInteresDTO = new PuntoInteresDTO();
        PuntoInteresDTO updatedPuntoInteresDTO = new PuntoInteresDTO();

        when(puntoInteresService.actualizarPunto(id, puntoInteresDTO)).thenReturn(updatedPuntoInteresDTO);

        ResponseEntity<PuntoInteresDTO> response = puntoInteresController.actualizarPunto(id, puntoInteresDTO);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(updatedPuntoInteresDTO, response.getBody());
        verify(puntoInteresService, times(1)).actualizarPunto(id, puntoInteresDTO);
    }

    @Test
    void eliminarPunto_ShouldReturnNoContent_WhenDeleted() {
        Long id = 1L;

        doNothing().when(puntoInteresService).eliminarPunto(id);

        ResponseEntity<Void> response = puntoInteresController.eliminarPunto(id);

        assertNotNull(response);
        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(puntoInteresService, times(1)).eliminarPunto(id);
    }
}
