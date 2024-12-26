package com.atours.atours_backend.archivo.domain;

import com.atours.atours_backend.config.audit.Auditable;
import com.atours.atours_backend.puntointeres.domain.PuntoInteres;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Archivo extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String ruta;

    @ManyToOne
    @JoinColumn(name = "punto_interes_id")
    private PuntoInteres puntoInteres;

}
