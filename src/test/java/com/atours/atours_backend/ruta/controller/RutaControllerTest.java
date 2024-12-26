package com.atours.atours_backend.ruta.controller;

import com.atours.atours_backend.ruta.dto.RutaDTO;
import com.atours.atours_backend.ruta.service.RutaServiceImpl;
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
import static org.springframework.http.HttpStatus.*;

class RutaControllerTest {

    @InjectMocks
    private RutaController rutaController;

    @Mock
    private RutaServiceImpl rutaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRutas_ShouldReturnListOfRutaDTOs() {
        RutaDTO rutaDTO = new RutaDTO();
        List<RutaDTO> rutas = Arrays.asList(rutaDTO);

        when(rutaService.getAllRutas()).thenReturn(rutas);

        ResponseEntity<List<RutaDTO>> response = rutaController.getAllRutas();

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        verify(rutaService, times(1)).getAllRutas();
    }

    @Test
    void getRutaById_ShouldReturnRutaDTO_WhenRutaExists() {
        Long id = 1L;
        RutaDTO rutaDTO = new RutaDTO();

        when(rutaService.getRutaById(id)).thenReturn(rutaDTO);

        ResponseEntity<RutaDTO> response = rutaController.getRutaById(id);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(rutaDTO, response.getBody());
        verify(rutaService, times(1)).getRutaById(id);
    }

    @Test
    void getRutaById_ShouldReturnNotFound_WhenRutaDoesNotExist() {
        Long id = 1L;

        when(rutaService.getRutaById(id)).thenReturn(null);

        ResponseEntity<RutaDTO> response = rutaController.getRutaById(id);

        assertNotNull(response);
        assertEquals(NOT_FOUND, response.getStatusCode());
        verify(rutaService, times(1)).getRutaById(id);
    }

    @Test
    void createRuta_ShouldReturnCreatedRutaDTO() {
        RutaDTO rutaDTO = new RutaDTO();
        RutaDTO createdRuta = new RutaDTO();

        when(rutaService.createRuta(rutaDTO)).thenReturn(createdRuta);

        ResponseEntity<RutaDTO> response = rutaController.createRuta(rutaDTO);

        assertNotNull(response);
        assertEquals(CREATED, response.getStatusCode());
        assertEquals(createdRuta, response.getBody());
        verify(rutaService, times(1)).createRuta(rutaDTO);
    }

    @Test
    void updateRuta_ShouldReturnUpdatedRutaDTO_WhenRutaExists() {
        Long id = 1L;
        RutaDTO rutaDTO = new RutaDTO();
        RutaDTO updatedRuta = new RutaDTO();

        when(rutaService.updateRuta(id, rutaDTO)).thenReturn(updatedRuta);

        ResponseEntity<RutaDTO> response = rutaController.updateRuta(id, rutaDTO);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(updatedRuta, response.getBody());
        verify(rutaService, times(1)).updateRuta(id, rutaDTO);
    }

    @Test
    void updateRuta_ShouldReturnNotFound_WhenRutaDoesNotExist() {
        Long id = 1L;
        RutaDTO rutaDTO = new RutaDTO();

        when(rutaService.updateRuta(id, rutaDTO)).thenReturn(null);

        ResponseEntity<RutaDTO> response = rutaController.updateRuta(id, rutaDTO);

        assertNotNull(response);
        assertEquals(NOT_FOUND, response.getStatusCode());
        verify(rutaService, times(1)).updateRuta(id, rutaDTO);
    }

    @Test
    void deleteRuta_ShouldReturnNoContent_WhenRutaExists() {
        Long id = 1L;

        when(rutaService.deleteRuta(id)).thenReturn(true);

        ResponseEntity<Void> response = rutaController.deleteRuta(id);

        assertNotNull(response);
        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(rutaService, times(1)).deleteRuta(id);
    }

    @Test
    void deleteRuta_ShouldReturnNotFound_WhenRutaDoesNotExist() {
        Long id = 1L;

        when(rutaService.deleteRuta(id)).thenReturn(false);

        ResponseEntity<Void> response = rutaController.deleteRuta(id);

        assertNotNull(response);
        assertEquals(NOT_FOUND, response.getStatusCode());
        verify(rutaService, times(1)).deleteRuta(id);
    }

    @Test
    void addPuntoInteresToRuta_ShouldReturnUpdatedRutaDTO_WhenSuccessful() {
        Long rutaId = 1L;
        Long puntoInteresId = 2L;
        RutaDTO updatedRuta = new RutaDTO();

        when(rutaService.addPuntoInteresToRuta(rutaId, puntoInteresId)).thenReturn(updatedRuta);

        ResponseEntity<RutaDTO> response = rutaController.addPuntoInteresToRuta(rutaId, puntoInteresId);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(updatedRuta, response.getBody());
        verify(rutaService, times(1)).addPuntoInteresToRuta(rutaId, puntoInteresId);
    }

    @Test
    void removePuntoInteresFromRuta_ShouldReturnNoContent_WhenSuccessful() {
        Long rutaId = 1L;
        Long puntoInteresId = 2L;

        when(rutaService.removePuntoInteresFromRuta(rutaId, puntoInteresId)).thenReturn(true);

        ResponseEntity<Void> response = rutaController.removePuntoInteresFromRuta(rutaId, puntoInteresId);

        assertNotNull(response);
        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(rutaService, times(1)).removePuntoInteresFromRuta(rutaId, puntoInteresId);
    }

    @Test
    void removePuntoInteresFromRuta_ShouldReturnNotFound_WhenNotSuccessful() {
        Long rutaId = 1L;
        Long puntoInteresId = 2L;

        when(rutaService.removePuntoInteresFromRuta(rutaId, puntoInteresId)).thenReturn(false);

        ResponseEntity<Void> response = rutaController.removePuntoInteresFromRuta(rutaId, puntoInteresId);

        assertNotNull(response);
        assertEquals(NOT_FOUND, response.getStatusCode());
        verify(rutaService, times(1)).removePuntoInteresFromRuta(rutaId, puntoInteresId);
    }
}
