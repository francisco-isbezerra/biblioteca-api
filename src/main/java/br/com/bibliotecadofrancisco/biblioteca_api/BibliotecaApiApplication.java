package br.com.bibliotecadofrancisco.biblioteca_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"br.com.bibliotecadofrancisco"})
@EnableJpaRepositories(basePackages = {"br.com.bibliotecadofrancisco"})
@ComponentScan(basePackages = {"br.com.bibliotecadofrancisco"})
public class BibliotecaApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApiApplication.class, args);
	}
}