package com.atours.atours_backend.archivo.service;

import com.atours.atours_backend.archivo.domain.Archivo;
import com.atours.atours_backend.archivo.domain.mapper.ArchivoMapper;
import com.atours.atours_backend.archivo.domain.repository.ArchivoRepository;
import com.atours.atours_backend.archivo.dto.ArchivoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implementación de la interfaz IArchivoService que define los métodos para manejar archivos.
 */
@Service
public class ArchivoServiceImpl implements IArchivoService {

    private final ArchivoRepository archivoRepository;
    private final ArchivoMapper archivoMapper;

    /**
     * Constructor de la clase ArchivoServiceImpl.
     *
     * @param archivoRepository Repositorio de archivos.
     * @param archivoMapper     Mapper para convertir entre entidades y DTOs de archivos.
     */
    @Autowired
    public ArchivoServiceImpl(ArchivoRepository archivoRepository, ArchivoMapper archivoMapper) {
        this.archivoRepository = archivoRepository;
        this.archivoMapper = archivoMapper;
    }

    /**
     * Guarda un archivo en la base de datos.
     *
     * @param archivo DTO del archivo a guardar.
     * @return DTO del archivo guardado.
     */
    @Override
    public ArchivoDTO guardarArchivo(ArchivoDTO archivo) {
        Archivo archivoGuardado = archivoRepository.save(archivoMapper.toEntity(archivo));
        return archivoMapper.toDto(archivoGuardado);
    }

    /**
     * Obtiene un archivo de la base de datos por su ID.
     *
     * @param archivoId ID del archivo a obtener.
     * @return DTO del archivo obtenido.
     * @throws ResponseStatusException Si el archivo no se encuentra en la base de datos.
     */
    @Override
    public ArchivoDTO obtenerArchivoPorId(Long archivoId) {
        Archivo archivo = archivoRepository.findById(archivoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Archivo no encontrado."));
        return archivoMapper.toDto(archivo);
    }

    /**
     * Elimina un archivo de la base de datos.
     *
     * @param archivo Archivo a eliminar.
     */
    @Override
    public void eliminarArchivo(Archivo archivo) {
        archivoRepository.delete(archivo);
    }
}
