package com.atours.atours_backend.puntointeres.controller;

import com.atours.atours_backend.archivo.service.ArchivoServiceImpl;
import com.atours.atours_backend.puntointeres.dto.PuntoInteresDTO;
import com.atours.atours_backend.puntointeres.service.IPuntoInteresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puntos-de-interes")
@Tag(name = "Puntos de Interés", description = "API para gestionar puntos de interés")
public class PuntoInteresController {

    private final IPuntoInteresService IPuntoInteresService;
    private final ArchivoServiceImpl archivoService;

    public PuntoInteresController(IPuntoInteresService IPuntoInteresService, ArchivoServiceImpl archivoService) {
        this.IPuntoInteresService = IPuntoInteresService;
        this.archivoService = archivoService;
    }

    @Operation(summary = "Crear un nuevo Punto de Interés")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Punto de Interés creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<PuntoInteresDTO> crearPunto(@Valid @RequestBody PuntoInteresDTO puntoInteresDTO) {
        return ResponseEntity.ok(IPuntoInteresService.crearPunto(puntoInteresDTO));
    }

    @Operation(summary = "Obtener todos los Puntos de Interés")
    @GetMapping
    public ResponseEntity<List<PuntoInteresDTO>> obtenerTodos() {
        return ResponseEntity.ok(IPuntoInteresService.obtenerTodos());
    }

    @Operation(summary = "Obtener un Punto de Interés por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PuntoInteresDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(IPuntoInteresService.obtenerPorId(id));
    }

    @Operation(summary = "Actualizar un Punto de Interés")
    @PutMapping("/{id}")
    public ResponseEntity<PuntoInteresDTO> actualizarPunto(@PathVariable Long id, @Valid @RequestBody PuntoInteresDTO puntoInteresDTO) {
        return ResponseEntity.ok(IPuntoInteresService.actualizarPunto(id, puntoInteresDTO));
    }

    @Operation(summary = "Eliminar un Punto de Interés")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPunto(@PathVariable Long id) {
        IPuntoInteresService.eliminarPunto(id);
        return ResponseEntity.noContent().build();
    }
}