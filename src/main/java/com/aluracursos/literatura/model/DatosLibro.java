package com.aluracursos.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosLibro{
    @JsonAlias("id") Long id;
    @JsonAlias("title") String title;
    @JsonAlias("authors") List<DatosAutor> authors;
    @JsonAlias("languages") List<String> languages;
    @JsonAlias("download_count") Long download_count;


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

    public List<DatosAutor> getAuthors() {
        return authors;
    }

    public void setAuthors(List<DatosAutor> authors) {
        this.authors = authors;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Long getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Long download_count) {
        this.download_count = download_count;
    }

    @Override
    public String toString() {
        return "----------------Libro----------------\nTitulo: "+this.getTitle()+"\nAutores: " +
                "\n"+this.getAuthors()+"\nIdioma: "+this.getLanguages()+"\nNumeros Descargados: "+this.getDownload_count() + "\n-------------------------------------";
    }
}
