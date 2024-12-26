package com.atours.atours_backend.ruta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RutaDTO {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private Double duracion;
    private Double distancia;
}
