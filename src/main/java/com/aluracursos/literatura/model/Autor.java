package com.aluracursos.literatura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(
        name = "autor",
        uniqueConstraints = { @UniqueConstraint(name = "uc_autores_nombre", columnNames =
                {"name"}) }
)
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=128)
    private String name;

    @Column(name = "birth_year", nullable=true)
    private Integer birthYear;

    @Column(name = "death_year", nullable=true)
    private Integer deathYear;

    @ManyToMany(mappedBy = "autores", fetch = FetchType.LAZY)
    private List<Libro> libros;

    public Autor() {}



    public Autor(String name, Integer birth_year, Integer death_year) {
        this.name = name;
        this.birthYear = birth_year;
        this.deathYear = death_year;
    }

    public Autor(String name, String birthYear, String deathYear) {
        this.name = name;
        this.birthYear = birthYear.equals("null") ? null : Integer.parseInt(birthYear);
        this.deathYear = deathYear.equals("null") ? null : Integer.parseInt(deathYear);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "----------------Autor----------------\nNombre: "+this.getName() + "\nBirth_year: "+ this.getBirthYear()+ "\nDeath_year: "+ this.getDeathYear()+ "\n-------------------------------------\n";
    }
}