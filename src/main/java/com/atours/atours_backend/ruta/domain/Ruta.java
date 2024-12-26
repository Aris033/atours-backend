package com.atours.atours_backend.ruta.domain;

import com.atours.atours_backend.config.audit.Auditable;
import com.atours.atours_backend.puntointeres.domain.PuntoInteres;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ruta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ruta extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Double duracion;

    @Column(nullable = false)
    private Double distancia;

    @ManyToMany
    @JoinTable(
            name = "ruta_punto_de_interes",
            joinColumns = @JoinColumn(name = "ruta_id"),
            inverseJoinColumns = @JoinColumn(name = "punto_de_interes_id")
    )
    private Set<PuntoInteres> puntosDeInteres = new HashSet<>();
}
