package com.aluracursos.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosAutor {
   @JsonAlias("name") String name;
    @JsonAlias("birth_year") String birth_year;
    @JsonAlias("death_year") String death_year;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birt_year) {
        this.birth_year = birt_year;
    }

    public String getDeath_year() {
        return death_year;
    }

    public void setDeath_year(String death_year) {
        this.death_year = death_year;
    }

    @Override
    public String toString() {
        return "----------------Autor----------------\nNombre: "+this.getName() + "\nBirth_year: "+ this.getBirth_year()+ "\nDeath_year: "+ this.getDeath_year()+ "\n-------------------------------------\n";
    }
}
