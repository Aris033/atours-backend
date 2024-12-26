package com.atours.atours_backend.ruta.service;

import com.atours.atours_backend.puntointeres.domain.PuntoInteres;
import com.atours.atours_backend.puntointeres.domain.repository.PuntoInteresRepository;
import com.atours.atours_backend.ruta.domain.Ruta;
import com.atours.atours_backend.ruta.domain.mapper.RutaMapper;
import com.atours.atours_backend.ruta.domain.repository.RutaRepository;
import com.atours.atours_backend.ruta.dto.RutaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RutaServiceImpl implements IRutaService {

    private final RutaRepository rutaRepository;
    private final PuntoInteresRepository puntoInteresRepository;
    private final RutaMapper rutaMapper;

    /**
     * Constructor de la clase RutaServiceImpl.
     *
     * @param rutaRepository         el repositorio de rutas.
     * @param puntoInteresRepository el repositorio de puntos de interés.
     * @param rutaMapper             el mapeador de rutas.
     */
    @Autowired
    public RutaServiceImpl(RutaRepository rutaRepository, PuntoInteresRepository puntoInteresRepository, RutaMapper rutaMapper) {
        this.rutaRepository = rutaRepository;
        this.puntoInteresRepository = puntoInteresRepository;
        this.rutaMapper = rutaMapper;
    }

    /**
     * Crea una nueva ruta.
     *
     * @param rutaDTO la información de la ruta a crear.
     * @return la ruta creada.
     */
    @Override
    public RutaDTO createRuta(RutaDTO rutaDTO) {
        Ruta ruta = rutaMapper.toEntity(rutaDTO);
        ruta = rutaRepository.save(ruta);
        return rutaMapper.toDto(ruta);
    }

    /**
     * Obtiene una ruta por su identificador.
     *
     * @param id el identificador de la ruta a obtener.
     * @return la ruta obtenida.
     * @throws ResponseStatusException si la ruta no se encuentra.
     */
    @Override
    public RutaDTO getRutaById(Long id) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ruta no encontrada con id: " + id));
        return rutaMapper.toDto(ruta);
    }

    /**
     * Obtiene todas las rutas.
     *
     * @return la lista de rutas.
     */
    @Override
    public List<RutaDTO> getAllRutas() {
        return rutaRepository.findAll()
                .stream()
                .map(rutaMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza una ruta existente.
     *
     * @param id      el identificador de la ruta a actualizar.
     * @param rutaDTO la información actualizada de la ruta.
     * @return la ruta actualizada.
     * @throws ResponseStatusException si la ruta no se encuentra.
     */
    @Override
    public RutaDTO updateRuta(Long id, RutaDTO rutaDTO) {
        Ruta existingRuta = rutaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ruta no encontrada con id: " + id));

        existingRuta.setNombre(rutaDTO.getNombre());
        existingRuta.setDescripcion(rutaDTO.getDescripcion());

        Ruta updatedRuta = rutaRepository.save(existingRuta);
        return rutaMapper.toDto(updatedRuta);
    }

    /**
     * Elimina una ruta existente.
     *
     * @param id el identificador de la ruta a eliminar.
     * @return true si la ruta se eliminó correctamente, false en caso contrario.
     * @throws ResponseStatusException si la ruta no se encuentra.
     */
    @Override
    public boolean deleteRuta(Long id) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ruta no encontrada con id: " + id));
        rutaRepository.delete(ruta);
        return true;
    }

    /**
     * Añade un punto de interés a una ruta existente.
     *
     * @param rutaId         el identificador de la ruta a la que se añadirá el punto de interés.
     * @param puntoInteresId el identificador del punto de interés a añadir.
     * @return la ruta actualizada.
     * @throws ResponseStatusException si la ruta o el punto de interés no se encuentran.
     */
    @Override
    public RutaDTO addPuntoInteresToRuta(Long rutaId, Long puntoInteresId) {
        Ruta ruta = rutaRepository.findById(rutaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ruta no encontrada con id: " + rutaId));
        PuntoInteres puntoInteres = puntoInteresRepository.findById(puntoInteresId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Punto de interés no encontrado con id: " + puntoInteresId));
        ruta.getPuntosDeInteres().add(puntoInteres);
        rutaRepository.save(ruta);
        return rutaMapper.toDto(ruta);
    }

    /**
     * Elimina un punto de interés de una ruta existente.
     *
     * @param rutaId         el identificador de la ruta de la que se eliminará el punto de interés.
     * @param puntoInteresId el identificador del punto de interés a eliminar.
     * @return true si el punto de interés se eliminó correctamente, false en caso contrario.
     * @throws ResponseStatusException si la ruta o el punto de interés no se encuentran.
     */
    @Override
    public boolean removePuntoInteresFromRuta(Long rutaId, Long puntoInteresId) {
        Ruta ruta = rutaRepository.findById(rutaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ruta no encontrada con id: " + rutaId));
        PuntoInteres puntoInteres = puntoInteresRepository.findById(puntoInteresId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Punto de interés no encontrado con id: " + puntoInteresId));
        ruta.getPuntosDeInteres().remove(puntoInteres);
        rutaRepository.save(ruta);
        return true;
    }
}
