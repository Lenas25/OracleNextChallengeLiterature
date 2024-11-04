package com.aluracursos.literatura.dto;

import java.util.List;

public record LibroDto(
        Long id,
        String tittle,
        Long download_count,
        List<AutorDto> authors,
        List<IdiomaDto> idiomas
) {
}
