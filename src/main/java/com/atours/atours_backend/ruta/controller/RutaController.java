package com.atours.atours_backend.ruta.controller;

import com.atours.atours_backend.ruta.dto.RutaDTO;
import com.atours.atours_backend.ruta.service.RutaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para la gestión de rutas.
 */
@RestController
@RequestMapping("/api/ruta")
@Tag(name = "Ruta", description = "API para gestionar rutas")
public class RutaController {

    private final RutaServiceImpl rutaService;

    public RutaController(RutaServiceImpl rutaService) {
        this.rutaService = rutaService;
    }

    @GetMapping()
    @Operation(summary = "Obtiene todas las rutas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rutas obtenidas exitosamente",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RutaDTO.class)))}),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)})
    public ResponseEntity<List<RutaDTO>> getAllRutas() {
        List<RutaDTO> rutas = rutaService.getAllRutas();
        return ResponseEntity.ok(rutas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una ruta por su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ruta obtenida exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RutaDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Ruta no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)})
    public ResponseEntity<RutaDTO> getRutaById(@PathVariable Long id) {
        RutaDTO ruta = rutaService.getRutaById(id);
        if (ruta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ruta);
    }

    @PostMapping()
    @Operation(summary = "Crea una nueva ruta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ruta creada exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RutaDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)})
    public ResponseEntity<RutaDTO> createRuta(
            @Valid @RequestBody RutaDTO rutaDTO) {
        RutaDTO createdRuta = rutaService.createRuta(rutaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRuta);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una ruta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ruta actualizada exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RutaDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Ruta no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)})
    public ResponseEntity<RutaDTO> updateRuta(
            @PathVariable Long id,
            @Valid @RequestBody RutaDTO rutaDTO) {
        RutaDTO updatedRuta = rutaService.updateRuta(id, rutaDTO);
        if (updatedRuta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedRuta);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una ruta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ruta eliminada exitosamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Ruta no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)})
    public ResponseEntity<Void> deleteRuta(@PathVariable Long id) {
        boolean deleted = rutaService.deleteRuta(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{rutaId}/puntos-interes/{puntoInteresId}")
    @Operation(summary = "Añade un punto de interés a una ruta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Punto de interés añadido exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RutaDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Ruta o punto de interés no encontrados",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)})
    public ResponseEntity<RutaDTO> addPuntoInteresToRuta(
            @PathVariable Long rutaId,
            @PathVariable Long puntoInteresId) {
        RutaDTO updatedRuta = rutaService.addPuntoInteresToRuta(rutaId, puntoInteresId);
        if (updatedRuta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedRuta);
    }

    @DeleteMapping("/{rutaId}/puntos-interes/{puntoInteresId}")
    @Operation(summary = "Elimina un punto de interés de una ruta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Punto de interés eliminado exitosamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Ruta o punto de interés no encontrados",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)})
    public ResponseEntity<Void> removePuntoInteresFromRuta(
            @PathVariable Long rutaId,
            @PathVariable Long puntoInteresId) {
        boolean removed = rutaService.removePuntoInteresFromRuta(rutaId, puntoInteresId);
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}