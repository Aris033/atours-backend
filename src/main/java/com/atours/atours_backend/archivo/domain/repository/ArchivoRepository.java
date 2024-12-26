package com.atours.atours_backend.archivo.domain.repository;

import com.atours.atours_backend.archivo.domain.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {
    List<Archivo> findByPuntoInteresId(Long puntoInteresId);
}
