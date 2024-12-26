package com.atours.atours_backend.archivo.service;

import com.atours.atours_backend.archivo.domain.Archivo;
import com.atours.atours_backend.archivo.dto.ArchivoDTO;

public interface IArchivoService {
    ArchivoDTO guardarArchivo(ArchivoDTO archivo);

    ArchivoDTO obtenerArchivoPorId(Long archivoId);

    void eliminarArchivo(Archivo archivo);
}
