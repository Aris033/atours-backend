package com.atours.atours_backend.puntointeres.domain.repository;

import com.atours.atours_backend.puntointeres.domain.PuntoInteres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuntoInteresRepository extends JpaRepository<PuntoInteres, Long> {
}
