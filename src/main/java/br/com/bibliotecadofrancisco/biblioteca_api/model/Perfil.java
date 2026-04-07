package br.com.bibliotecadofrancisco.bibliotecaapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bio;
    private String redeSocialUrl;
}