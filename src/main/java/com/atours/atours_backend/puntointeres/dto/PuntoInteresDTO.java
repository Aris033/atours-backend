package com.atours.atours_backend.puntointeres.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Punto interes")
public class PuntoInteresDTO {

    private Long id;

    @NotNull(message = "El nombre no puede estar vacío.")
    private String nombre;

    @NotNull(message = "La descripción no puede estar vacía.")
    private String descripcion;

    @NotNull(message = "La latitud no puede estar vacía.")
    @DecimalMin(value = "-90.000000", message = "La latitud debe ser mayor o igual a -90.")
    @DecimalMax(value = "90.000000", message = "La latitud debe ser menor o igual a 90.")
    private BigDecimal latitud;

    @NotNull(message = "La longitud no puede estar vacía.")
    @DecimalMin(value = "-180.000000", message = "La longitud debe ser mayor o igual a -180.")
    @DecimalMax(value = "180.000000", message = "La longitud debe ser menor o igual a 180.")
    private BigDecimal longitud;

    @NotNull(message = "La categoría no puede estar vacía.")
    private String categoria;
}

