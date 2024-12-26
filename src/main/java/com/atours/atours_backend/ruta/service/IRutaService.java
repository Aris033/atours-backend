package com.atours.atours_backend.ruta.service;

import com.atours.atours_backend.ruta.dto.RutaDTO;

import java.util.List;

public interface IRutaService {
    RutaDTO createRuta(RutaDTO rutaDTO);

    RutaDTO getRutaById(Long id);

    List<RutaDTO> getAllRutas();

    RutaDTO updateRuta(Long id, RutaDTO rutaDTO);

    boolean deleteRuta(Long id);

    RutaDTO addPuntoInteresToRuta(Long rutaId, Long puntoInteresId);

    boolean removePuntoInteresFromRuta(Long rutaId, Long puntoInteresId);
}
