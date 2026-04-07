package br.com.bibliotecadofrancisco.bibliotecaapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    @Enumerated(EnumType.STRING) // Salva o nome da categoria (ex: "TERROR") no banco em vez do número do índice
    private Categoria categoria;

    // ManyToOne: Muitos livros podem pertencer a um único Autor.
    @ManyToOne
    @JoinColumn(name = "autor_id") // Nome da coluna que guarda a referência do autor no banco
    private Autor autor;
}