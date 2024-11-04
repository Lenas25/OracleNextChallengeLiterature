package com.aluracursos.literatura.repository;

import com.aluracursos.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByIdiomas_Code(String code);

    @Query("SELECT l FROM Libro l ORDER BY l.download_count DESC " +
            "LIMIT 10")
    List<Libro> top10Libros();

}
