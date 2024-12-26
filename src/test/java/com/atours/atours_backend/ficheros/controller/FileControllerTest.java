package com.atours.atours_backend.ficheros.controller;

import com.atours.atours_backend.archivo.dto.ArchivoDTO;
import com.atours.atours_backend.ficheros.service.FileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

class FileControllerTest {

    @InjectMocks
    private FileController fileController;

    @Mock
    private FileServiceImpl fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadFile_ShouldReturnCreatedStatus_WhenFileIsUploaded() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test content".getBytes());
        Long puntoInteresId = 1L;
        ArchivoDTO mockArchivoDTO = new ArchivoDTO(); // Simulación del objeto de retorno

        when(fileService.subirArchivo(file, puntoInteresId)).thenReturn(mockArchivoDTO);

        ResponseEntity<String> response = fileController.uploadFile(puntoInteresId, file);

        assertEquals(CREATED, response.getStatusCode());
        assertEquals("Archivo subido con éxito", response.getBody());
        verify(fileService, times(1)).subirArchivo(file, puntoInteresId);
    }

    @Test
    void updateFile_ShouldReturnOkStatus_WhenFileIsUpdated() {
        MockMultipartFile file = new MockMultipartFile("file", "updated.txt", "text/plain", "updated content".getBytes());
        Long archivoId = 1L;
        Long puntoInteresId = 2L;
        ArchivoDTO mockArchivoDTO = new ArchivoDTO(); // Simulación del objeto de retorno

        when(fileService.actualizarArchivo(archivoId, file, puntoInteresId)).thenReturn(mockArchivoDTO);

        ResponseEntity<String> response = fileController.updateFile(archivoId, puntoInteresId, file);

        assertEquals(OK, response.getStatusCode());
        assertEquals("Archivo actualizado con éxito", response.getBody());
        verify(fileService, times(1)).actualizarArchivo(archivoId, file, puntoInteresId);
    }


    @Test
    void deleteFile_ShouldReturnNoContent_WhenFileIsDeleted() {
        Long archivoId = 1L;

        doNothing().when(fileService).eliminarArchivo(archivoId);

        ResponseEntity<Void> response = fileController.deleteFile(archivoId);

        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(fileService, times(1)).eliminarArchivo(archivoId);
    }
}
