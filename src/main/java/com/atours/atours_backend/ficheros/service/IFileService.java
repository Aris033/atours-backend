package com.atours.atours_backend.ficheros.service;

import com.atours.atours_backend.archivo.dto.ArchivoDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    ArchivoDTO actualizarArchivo(Long archivoId, MultipartFile nuevoArchivo, Long puntoInteresId);
}
