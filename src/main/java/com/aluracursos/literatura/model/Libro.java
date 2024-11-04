package com.aluracursos.literatura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(
        name = "libro",
        uniqueConstraints = {@UniqueConstraint(name = "uc_libros_id", columnNames = {"id"})}
)
public class Libro {

    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 1024)
    private String title;

    @Column(nullable = false)
    private Long download_count;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch =
            FetchType.EAGER)
    @JoinTable(
            name = "libros_autores",
            joinColumns = @JoinColumn(name = "libro_id", foreignKey = @ForeignKey(name = "fk_libros_autores_libro_id")),
            inverseJoinColumns = @JoinColumn(name = "autor_id", foreignKey = @ForeignKey(name = "fk_libros_autores_autor_id")))
    @OrderBy("name ASC")
    private List<Autor> autores;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libros_idiomas",
            joinColumns = @JoinColumn(name = "libro_id", foreignKey = @ForeignKey(name = "fk_libros_idiomas_libro_id")),
            inverseJoinColumns = @JoinColumn(name = "idioma_id", foreignKey = @ForeignKey(name = "fk_libros_idiomas_idioma_id")))
    @OrderBy("code ASC")
    private List<Idioma> idiomas;

    public  Libro(){}
    public Libro(DatosLibro libroNuevo) {
        this.id = libroNuevo.getId();
        this.title = libroNuevo.getTitle();
        this.download_count =  libroNuevo.getDownload_count();
        // Mapeo de autores
        this.autores = libroNuevo.getAuthors().stream()
                .map(a -> new Autor(a.getName(), a.getBirth_year(), a.getDeath_year()))
                .collect(Collectors.toList());

        // Mapeo de idiomas
        this.idiomas = libroNuevo.getLanguages().stream()
                .map(i -> new Idioma(i))
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Long download_count) {
        this.download_count = download_count;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<DatosIdioma> idiomas) {
        this.idiomas = idiomas.stream().map(i -> new Idioma(i.getCode())).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "----------------Libro----------------\nTitulo: "+this.getTitle()+"\nAutores: \n"+this.getAutores()+"\nIdioma: \n"+this.getIdiomas()+"\nNumeros Descargados: "+this.getDownload_count() + "\n-------------------------------------";
    }
}