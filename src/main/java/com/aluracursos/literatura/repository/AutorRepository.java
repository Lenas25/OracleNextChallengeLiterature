package com.aluracursos.literatura.repository;

import com.aluracursos.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByName(String name);

    List<Autor> findByDeathYearGreaterThanEqualAndBirthYearLessThan(Integer year, Integer year2);

}
