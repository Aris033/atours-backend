package com.atours.atours_backend.puntointeres.domain;

import com.atours.atours_backend.archivo.domain.Archivo;
import com.atours.atours_backend.config.audit.Auditable;
import com.atours.atours_backend.ruta.domain.Ruta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "punto_de_interes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PuntoInteres extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    @Column(precision = 9, scale = 6)
    private BigDecimal latitud;

    @Column(precision = 9, scale = 6)
    private BigDecimal longitud;

    private String categoria;

    @ManyToMany(mappedBy = "puntosDeInteres")
    private Set<Ruta> rutas = new HashSet<>();

    @OneToMany(mappedBy = "puntoInteres", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Archivo> archivos = new ArrayList<>();

}
