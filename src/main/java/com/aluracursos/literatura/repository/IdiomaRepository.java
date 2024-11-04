package com.aluracursos.literatura.repository;

import com.aluracursos.literatura.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdiomaRepository extends JpaRepository<Idioma, Long> {

    Optional<Idioma> findByCode(String code);
}
