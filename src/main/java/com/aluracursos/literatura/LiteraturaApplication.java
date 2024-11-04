package com.aluracursos.literatura;

import com.aluracursos.literatura.repository.*;
import com.aluracursos.literatura.view.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private IdiomaRepository idiomaRepository;


	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Menu menu = new Menu(libroRepository, autorRepository, idiomaRepository);
		menu.muestraElMenu();
	}
}
