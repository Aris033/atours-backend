package com.atours.atours_backend.ficheros.service;

import com.atours.atours_backend.archivo.domain.mapper.ArchivoMapper;
import com.atours.atours_backend.archivo.service.ArchivoServiceImpl;
import com.atours.atours_backend.puntointeres.domain.mapper.PuntoInteresMapper;
import com.atours.atours_backend.puntointeres.service.IPuntoInteresService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

class FileServiceImplTest {

    @InjectMocks
    private FileServiceImpl fileService;

    @Mock
    private ArchivoServiceImpl archivoService;

    @Mock
    private IPuntoInteresService puntoInteresService;

    @Mock
    private PuntoInteresMapper puntoInteresMapper;

    @Mock
    private ArchivoMapper archivoMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(fileService, "fileBasePath", "C:/temp/uploads"); // Establece una ruta válida

    }

}
