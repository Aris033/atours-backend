package com.atours.atours_backend.puntointeres.service;

import com.atours.atours_backend.puntointeres.domain.PuntoInteres;
import com.atours.atours_backend.puntointeres.domain.mapper.PuntoInteresMapper;
import com.atours.atours_backend.puntointeres.domain.repository.PuntoInteresRepository;
import com.atours.atours_backend.puntointeres.dto.PuntoInteresDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio IPuntoInteresService para gestionar operaciones
 * sobre puntos de interés.
 */
@Service
public class PuntoInteresServiceImpl implements IPuntoInteresService {

    private final PuntoInteresMapper puntoInteresMapper;
    private final PuntoInteresRepository repository;

    /**
     * Constructor que inicializa los componentes necesarios para el servicio.
     *
     * @param puntoInteresMapper Mapper para convertir entre entidades y DTOs.
     * @param repository         Repositorio para el acceso a la persistencia de puntos de interés.
     */
    @Autowired
    public PuntoInteresServiceImpl(PuntoInteresMapper puntoInteresMapper, PuntoInteresRepository repository) {
        this.puntoInteresMapper = puntoInteresMapper;
        this.repository = repository;
    }

    /**
     * Crea un nuevo punto de interés a partir de un DTO y lo guarda en la base de datos.
     *
     * @param puntoInteresDTO DTO que contiene la información del punto de interés a crear.
     * @return El DTO del punto de interés creado, con el ID asignado.
     */
    @Override
    public PuntoInteresDTO crearPunto(PuntoInteresDTO puntoInteresDTO) {
        PuntoInteres punto = puntoInteresMapper.toEntity(puntoInteresDTO);
        PuntoInteres savedPunto = repository.save(punto);
        return puntoInteresMapper.toDto(savedPunto);
    }

    /**
     * Obtiene una lista de todos los puntos de interés almacenados.
     *
     * @return Lista de DTOs de todos los puntos de interés.
     */
    @Override
    public List<PuntoInteresDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(puntoInteresMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un punto de interés específico por su ID.
     *
     * @param id Identificador del punto de interés a buscar.
     * @return DTO del punto de interés encontrado.
     * @throws ResponseStatusException si el punto de interés no existe.
     */
    @Override
    public PuntoInteresDTO obtenerPorId(Long id) {
        PuntoInteres punto = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Punto de Interés no encontrado"));
        return puntoInteresMapper.toDto(punto);
    }

    /**
     * Actualiza un punto de interés existente con los datos proporcionados en el DTO.
     *
     * @param id              Identificador del punto de interés a actualizar.
     * @param puntoInteresDTO DTO con la información a actualizar.
     * @return DTO del punto de interés actualizado.
     * @throws ResponseStatusException si el punto de interés no existe.
     */
    @Override
    public PuntoInteresDTO actualizarPunto(Long id, PuntoInteresDTO puntoInteresDTO) {
        PuntoInteres punto = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Punto de Interés no encontrado"));

        puntoInteresMapper.updateEntityFromDto(puntoInteresDTO, punto);
        PuntoInteres updatedPunto = repository.save(punto);
        return puntoInteresMapper.toDto(updatedPunto);
    }

    /**
     * Elimina un punto de interés por su ID.
     *
     * @param id Identificador del punto de interés a eliminar.
     * @throws ResponseStatusException si el punto de interés no existe.
     */
    @Override
    public void eliminarPunto(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Punto de Interés no encontrado");
        }
        repository.deleteById(id);
    }
}