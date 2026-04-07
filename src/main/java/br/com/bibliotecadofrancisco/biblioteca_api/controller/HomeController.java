package br.com.bibliotecadofrancisco.biblioteca_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "API da Biblioteca está online e funcionando!";
    }
}