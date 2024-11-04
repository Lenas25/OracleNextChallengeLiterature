package com.aluracursos.literatura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(
        name = "idioma",
        uniqueConstraints = { @UniqueConstraint(name = "uc_idiomas_codigo", columnNames =
                {"code"}) }
)
public class Idioma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=5)
    private String code;

    @ManyToMany(mappedBy = "idiomas", fetch = FetchType.LAZY)
    private List<Libro> libros;

    public Idioma() {}

    public Idioma(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return this.getCode();
    }
}
