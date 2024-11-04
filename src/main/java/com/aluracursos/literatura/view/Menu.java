package com.aluracursos.literatura.view;

import com.aluracursos.literatura.dto.AutorDto;
import com.aluracursos.literatura.dto.IdiomaDto;
import com.aluracursos.literatura.dto.LibroDto;
import com.aluracursos.literatura.model.*;
import com.aluracursos.literatura.repository.AutorRepository;
import com.aluracursos.literatura.repository.IdiomaRepository;
import com.aluracursos.literatura.repository.LibroRepository;
import com.aluracursos.literatura.service.ConsumoApi;
import com.aluracursos.literatura.service.ConvierteDatos;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;

import java.util.*;
import java.util.stream.Collectors;

public class Menu {

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private IdiomaRepository idiomaRepository;
    private Scanner scan = new Scanner(System.in);
    private ConsumoApi consumoAPI = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();
    private static final String URL_BASE = "https://gutendex.com/books/?search=";

    public Menu(LibroRepository libroRepository, AutorRepository autorRepository, IdiomaRepository idiomaRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.idiomaRepository = idiomaRepository;
    }

    public void muestraElMenu() throws JsonProcessingException {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Bienvenido a la Libreria
                    Escoja una opcion mediante numeros
                    1 - Buscar libro por titulo
                    2 - Mostrar libros registrados
                    3 - Mostrar autores registrados
                    4 - Buscar autores vivos hasta determinado año
                    5 - Mostrar libros por idioma
                    6 - Generar estadisticas
                    7 - Top 10 libros mas descargados
                    8 - Buscar autor por nombre
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = scan.nextInt();
            scan.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibrosWeb();
                    break;
                case 2:
                    mostrarLibros();
                    break;
                case 3:
                    mostrarAutores();
                    break;
                case 4:
                    actoresVivosPorAnio();
                    break;
                case 5:
                    librosPorIdioma();
                    break;
                case 6:
                    generarEstadisticas();
                    break;
                case 7:
                    top10Libros();
                    break;
                case 8:
                    buscarAutor();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private DatosLibro getDatosLibro() throws JsonProcessingException {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = scan.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        DatosLibro datos = conversor.obtenerDatos(json, DatosLibro.class);

        if (datos == null) {
            throw new IllegalArgumentException("Error: No se pudieron obtener los datos del libro.");
        }
        return datos;
    }

    private void buscarLibrosWeb() throws JsonProcessingException {
        DatosLibro libroNuevo = getDatosLibro();
        Libro libroModelo = new Libro(libroNuevo);
        // Guardar autores en la base de datos
        for (Autor autor : libroModelo.getAutores()) {
            Optional<Autor> autorExistente = autorRepository.findByName(autor.getName());
            if (autorExistente.isPresent()) {
                // Usa el ID del autor existente y mofica los autores del libro
                autor.setId(autorExistente.get().getId());
            } else {
                // Guarda el autor en la base de datos
                autorRepository.save(autor);
            }
        }

        // Guardar idiomas en la base de datos
        for (Idioma idioma : libroModelo.getIdiomas()) {
            Optional<Idioma> idiomaExistente = idiomaRepository.findByCode(idioma.getCode());
            if (idiomaExistente.isPresent()) {
                // Usa el ID del idioma existente y modifica los idiomas del libro
                idioma.setId(idiomaExistente.get().getId()); // Usa el ID del idioma existente
            } else {
                // Guarda el idioma en la base de datos
                idiomaRepository.save(idioma);
            }
        }

        // Guardar el libro en la base de datos
        libroRepository.save(libroModelo);
        System.out.println(libroNuevo);
    }

    private void mostrarLibros() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }

    private void mostrarAutores() {
        List<Autor> autores = autorRepository.findAll();
        autores.stream().sorted(Comparator.comparing(Autor::getBirthYear)).forEach(System.out::println);
    }

    private void actoresVivosPorAnio() {
        System.out.println("Escribe el año que deseas buscar");
        var anio = scan.nextLine();
        List<Autor> autores =
                autorRepository.findByDeathYearGreaterThanEqualAndBirthYearLessThan(Integer.parseInt(anio), Integer.parseInt(anio));
        if(autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio);
        }else{
            autores.stream().sorted(Comparator.comparing(Autor::getBirthYear)).forEach(System.out::println);
        }
    }

    private void librosPorIdioma(){
        System.out.println("Escribe el idioma que deseas buscar");
        var idioma = scan.nextLine();
        List<Libro> libros = libroRepository.findByIdiomas_Code(idioma);
        if(libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma " + idioma);
        }else{
            libros.forEach(System.out::println);
        }
    }

    private void generarEstadisticas(){
        System.out.println("Estadisticas");
        System.out.println("Libros: ");
        DoubleSummaryStatistics stats = libroRepository.findAll().stream().collect(Collectors.summarizingDouble(Libro::getDownload_count));
        System.out.println("Total de libros: " + stats.getCount());
    }

    private void top10Libros(){
        List<Libro> libros = libroRepository.top10Libros();
        libros.forEach(l-> System.out.println(l.getTitle() + " - " + l.getDownload_count()));
    }

    private void buscarAutor(){
        System.out.println("Escribe el nombre del autor que deseas buscar");
        var nombreAutor = scan.nextLine();
        Optional<Autor> autor = autorRepository.findByName(nombreAutor);
        if(autor.isPresent()){
            System.out.println(autor.get());
        }else{
            System.out.println("No se encontró el autor " + nombreAutor);
        }
    }
}
