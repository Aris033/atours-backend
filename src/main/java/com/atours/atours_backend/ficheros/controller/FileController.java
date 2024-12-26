package com.atours.atours_backend.ficheros.controller;

import com.atours.atours_backend.ficheros.service.FileServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@Tag(name = "Gestión Ficheros", description = "Endpoint para gestionar archivos")
public class FileController {

    private final FileServiceImpl fileService;

    public FileController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    // Método para subir un archivo
    @Operation(summary = "Sube un archivo para un Punto de Interés")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Archivo subido con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o error al subir archivo")
    })
    @PostMapping(value = "/PuntosDeInteres/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
            @Parameter(description = "ID del Punto de Interés", required = true)
            @PathVariable("id") Long puntoInteresId,
            @Parameter(description = "Archivo a subir", required = true, content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam("file") MultipartFile file) {

        fileService.subirArchivo(file, puntoInteresId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Archivo subido con éxito");
    }

    // Método para actualizar un archivo
    @Operation(summary = "Actualiza un archivo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Archivo actualizado con éxito"),
            @ApiResponse(responseCode = "404", description = "Archivo no encontrado"),
            @ApiResponse(responseCode = "400", description = "Error al actualizar archivo")
    })
    @PutMapping(value = "/PuntosDeInteres/{puntoInteresId}/archivo/{archivoId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateFile(
            @Parameter(description = "ID del Archivo a actualizar", required = true)
            @PathVariable("archivoId") Long archivoId,
            @Parameter(description = "ID del Archivo a actualizar", required = true)
            @PathVariable("puntoInteresId") Long puntoInteresId,
            @Parameter(description = "Nuevo archivo", required = true, content = @io.swagger.v3.oas.annotations.media.Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestParam("file") MultipartFile newFile) {

        fileService.actualizarArchivo(archivoId, newFile, puntoInteresId);
        return ResponseEntity.ok("Archivo actualizado con éxito");
    }

    // Método para eliminar un archivo
    @Operation(summary = "Elimina un archivo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Archivo eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Archivo no encontrado"),
            @ApiResponse(responseCode = "400", description = "Error al eliminar archivo")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(
            @Parameter(description = "ID del Archivo a eliminar", required = true)
            @PathVariable("id") Long archivoId) {

        fileService.eliminarArchivo(archivoId);
        return ResponseEntity.noContent().build();
    }
}
