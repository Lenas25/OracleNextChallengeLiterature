package com.aluracursos.literatura.dto;

import java.util.List;

public record AutorDto(
        Long id,
        String name,
        Integer birth_year,
        Integer death_year,
        List<LibroDto> libros
) {
}
