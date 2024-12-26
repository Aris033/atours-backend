package com.atours.atours_backend.ficheros.service;

import com.atours.atours_backend.archivo.domain.Archivo;
import com.atours.atours_backend.archivo.domain.mapper.ArchivoMapper;
import com.atours.atours_backend.archivo.dto.ArchivoDTO;
import com.atours.atours_backend.archivo.service.ArchivoServiceImpl;
import com.atours.atours_backend.puntointeres.domain.PuntoInteres;
import com.atours.atours_backend.puntointeres.domain.mapper.PuntoInteresMapper;
import com.atours.atours_backend.puntointeres.dto.PuntoInteresDTO;
import com.atours.atours_backend.puntointeres.service.IPuntoInteresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementación de la interfaz IFileService que define los métodos para manejar archivos.
 */
@Service
public class FileServiceImpl implements IFileService {

    private final ArchivoServiceImpl archivoService;
    private final IPuntoInteresService IPuntoInteresService;
    private final PuntoInteresMapper puntoInteresMapper;
    private final ArchivoMapper archivoMapper;

    @Value("${file.upload-dir}")
    private String fileBasePath;

    /**
     * Constructor de la clase FileServiceImpl.
     *
     * @param archivoService       Servicio para manejar archivos.
     * @param IPuntoInteresService Servicio para manejar puntos de interés.
     * @param puntoInteresMapper   Mapper para convertir entre entidades y DTOs de puntos de interés.
     * @param archivoMapper        Mapper para convertir entre entidades y DTOs de archivos.
     */
    @Autowired
    public FileServiceImpl(ArchivoServiceImpl archivoService, IPuntoInteresService IPuntoInteresService, PuntoInteresMapper puntoInteresMapper, ArchivoMapper archivoMapper) {
        this.archivoService = archivoService;
        this.IPuntoInteresService = IPuntoInteresService;
        this.puntoInteresMapper = puntoInteresMapper;
        this.archivoMapper = archivoMapper;
    }

    /**
     * Sube un archivo al servidor y lo guarda en la base de datos.
     *
     * @param file           Archivo a subir.
     * @param puntoInteresId ID del punto de interés al que pertenece el archivo.
     * @return DTO del archivo guardado.
     * @throws IllegalArgumentException Si el archivo ya existe en la ruta especificada.
     * @throws RuntimeException         Si ocurre un error al copiar el archivo al servidor.
     */
    public ArchivoDTO subirArchivo(MultipartFile file, Long puntoInteresId) {
        PuntoInteresDTO puntoInteresDTO = IPuntoInteresService.obtenerPorId(puntoInteresId);
        PuntoInteres puntoInteres = puntoInteresMapper.toEntity(puntoInteresDTO);

        String filePath = fileBasePath + "/" + file.getOriginalFilename();
        File newFile = new File(filePath);

        if (newFile.exists()) {
            throw new IllegalArgumentException("El archivo ya existe en la ruta especificada.");
        }

        try {
            Files.copy(file.getInputStream(), Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Archivo archivo = new Archivo();
        archivo.setNombre(file.getOriginalFilename());
        archivo.setRuta(filePath);
        archivo.setPuntoInteres(puntoInteres);

        return archivoService.guardarArchivo(archivoMapper.toDto(archivo));
    }

    /**
     * Actualiza un archivo en el servidor y en la base de datos.
     *
     * @param archivoId      ID del archivo a actualizar.
     * @param nuevoArchivo   Nuevo archivo a subir.
     * @param puntoInteresId ID del punto de interés al que pertenece el archivo.
     * @return DTO del archivo actualizado.
     * @throws ResponseStatusException Si ocurre un error al eliminar el archivo existente.
     */
    @Override
    public ArchivoDTO actualizarArchivo(Long archivoId, MultipartFile nuevoArchivo, Long puntoInteresId) {
        ArchivoDTO archivoDTOExistente = archivoService.obtenerArchivoPorId(archivoId);
        Archivo archivoExistente = archivoMapper.toEntity(archivoDTOExistente);
        Path archivoRuta = Paths.get(archivoExistente.getRuta());

        try {
            Files.deleteIfExists(archivoRuta);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el archivo existente.", e);
        }

        subirArchivo(nuevoArchivo, puntoInteresId);
        archivoExistente.setNombre(nuevoArchivo.getOriginalFilename());
        archivoExistente.setRuta(fileBasePath + nuevoArchivo.getOriginalFilename());

        return archivoService.guardarArchivo(archivoMapper.toDto(archivoExistente));
    }

    /**
     * Elimina un archivo del servidor y de la base de datos.
     *
     * @param archivoId ID del archivo a eliminar.
     * @throws ResponseStatusException Si ocurre un error al eliminar el archivo físico.
     */
    public void eliminarArchivo(Long archivoId) {
        ArchivoDTO archivoDTO = archivoService.obtenerArchivoPorId(archivoId);
        Archivo archivo = archivoMapper.toEntity(archivoDTO);

        try {
            deleteFileFromSystem(archivo.getRuta());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el archivo físico.", e);
        }

        archivoService.eliminarArchivo(archivo);
    }

    public void deleteFileFromSystem(String filePath) throws IOException {
        Files.deleteIfExists(Paths.get(filePath));
    }
}