package com.atours.atours_backend.puntointeres.service;

import com.atours.atours_backend.puntointeres.dto.PuntoInteresDTO;

import java.util.List;

public interface IPuntoInteresService {
    PuntoInteresDTO crearPunto(PuntoInteresDTO puntoInteresDTO);

    List<PuntoInteresDTO> obtenerTodos();

    PuntoInteresDTO obtenerPorId(Long id);

    PuntoInteresDTO actualizarPunto(Long id, PuntoInteresDTO puntoInteresDTO);

    void eliminarPunto(Long id);
}
