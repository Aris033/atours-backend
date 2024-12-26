package com.atours.atours_backend.ruta.service;

import com.atours.atours_backend.puntointeres.domain.PuntoInteres;
import com.atours.atours_backend.puntointeres.domain.repository.PuntoInteresRepository;
import com.atours.atours_backend.ruta.domain.Ruta;
import com.atours.atours_backend.ruta.domain.mapper.RutaMapper;
import com.atours.atours_backend.ruta.domain.repository.RutaRepository;
import com.atours.atours_backend.ruta.dto.RutaDTO;
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

class RutaServiceImplTest {

    @InjectMocks
    private RutaServiceImpl rutaService;

    @Mock
    private RutaRepository rutaRepository;

    @Mock
    private PuntoInteresRepository puntoInteresRepository;

    @Mock
    private RutaMapper rutaMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRuta_ShouldReturnCreatedRutaDTO() {
        RutaDTO rutaDTO = new RutaDTO();
        Ruta ruta = new Ruta();

        when(rutaMapper.toEntity(rutaDTO)).thenReturn(ruta);
        when(rutaRepository.save(ruta)).thenReturn(ruta);
        when(rutaMapper.toDto(ruta)).thenReturn(rutaDTO);

        RutaDTO result = rutaService.createRuta(rutaDTO);

        assertNotNull(result);
        verify(rutaRepository, times(1)).save(ruta);
    }

    @Test
    void getRutaById_ShouldReturnRutaDTO_WhenRutaExists() {
        Long id = 1L;
        Ruta ruta = new Ruta();
        RutaDTO rutaDTO = new RutaDTO();

        when(rutaRepository.findById(id)).thenReturn(Optional.of(ruta));
        when(rutaMapper.toDto(ruta)).thenReturn(rutaDTO);

        RutaDTO result = rutaService.getRutaById(id);

        assertNotNull(result);
        verify(rutaRepository, times(1)).findById(id);
    }

    @Test
    void getRutaById_ShouldThrowException_WhenRutaNotFound() {
        Long id = 1L;

        when(rutaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> rutaService.getRutaById(id));
    }

    @Test
    void getAllRutas_ShouldReturnListOfRutaDTOs() {
        Ruta ruta = new Ruta();
        RutaDTO rutaDTO = new RutaDTO();
        List<Ruta> rutas = Arrays.asList(ruta);

        when(rutaRepository.findAll()).thenReturn(rutas);
        when(rutaMapper.toDto(ruta)).thenReturn(rutaDTO);

        List<RutaDTO> result = rutaService.getAllRutas();

        assertFalse(result.isEmpty());
        verify(rutaRepository, times(1)).findAll();
    }

    @Test
    void updateRuta_ShouldReturnUpdatedRutaDTO_WhenRutaExists() {
        Long id = 1L;
        Ruta existingRuta = new Ruta();
        RutaDTO rutaDTO = new RutaDTO();

        when(rutaRepository.findById(id)).thenReturn(Optional.of(existingRuta));
        when(rutaRepository.save(existingRuta)).thenReturn(existingRuta);
        when(rutaMapper.toDto(existingRuta)).thenReturn(rutaDTO);

        RutaDTO result = rutaService.updateRuta(id, rutaDTO);

        assertNotNull(result);
        verify(rutaRepository, times(1)).save(existingRuta);
    }

    @Test
    void updateRuta_ShouldThrowException_WhenRutaNotFound() {
        Long id = 1L;
        RutaDTO rutaDTO = new RutaDTO();

        when(rutaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> rutaService.updateRuta(id, rutaDTO));
    }

    @Test
    void deleteRuta_ShouldReturnTrue_WhenRutaExists() {
        Long id = 1L;
        Ruta ruta = new Ruta();

        when(rutaRepository.findById(id)).thenReturn(Optional.of(ruta));

        boolean result = rutaService.deleteRuta(id);

        assertTrue(result);
        verify(rutaRepository, times(1)).delete(ruta);
    }

    @Test
    void deleteRuta_ShouldThrowException_WhenRutaNotFound() {
        Long id = 1L;

        when(rutaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> rutaService.deleteRuta(id));
    }

    @Test
    void addPuntoInteresToRuta_ShouldReturnUpdatedRutaDTO_WhenRutaAndPuntoInteresExist() {
        Long rutaId = 1L;
        Long puntoInteresId = 2L;
        Ruta ruta = new Ruta();
        PuntoInteres puntoInteres = new PuntoInteres();
        RutaDTO rutaDTO = new RutaDTO();

        when(rutaRepository.findById(rutaId)).thenReturn(Optional.of(ruta));
        when(puntoInteresRepository.findById(puntoInteresId)).thenReturn(Optional.of(puntoInteres));
        when(rutaMapper.toDto(ruta)).thenReturn(rutaDTO);

        RutaDTO result = rutaService.addPuntoInteresToRuta(rutaId, puntoInteresId);

        assertNotNull(result);
        assertTrue(ruta.getPuntosDeInteres().contains(puntoInteres));
        verify(rutaRepository, times(1)).save(ruta);
    }

    @Test
    void removePuntoInteresFromRuta_ShouldReturnTrue_WhenRutaAndPuntoInteresExist() {
        Long rutaId = 1L;
        Long puntoInteresId = 2L;
        Ruta ruta = new Ruta();
        PuntoInteres puntoInteres = new PuntoInteres();
        ruta.getPuntosDeInteres().add(puntoInteres);

        when(rutaRepository.findById(rutaId)).thenReturn(Optional.of(ruta));
        when(puntoInteresRepository.findById(puntoInteresId)).thenReturn(Optional.of(puntoInteres));

        boolean result = rutaService.removePuntoInteresFromRuta(rutaId, puntoInteresId);

        assertTrue(result);
        assertFalse(ruta.getPuntosDeInteres().contains(puntoInteres));
        verify(rutaRepository, times(1)).save(ruta);
    }
}
