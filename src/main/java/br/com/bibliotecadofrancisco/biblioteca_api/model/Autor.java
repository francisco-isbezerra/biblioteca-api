package br.com.bibliotecadofrancisco.bibliotecaapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Entity // Define que esta classe é uma tabela no banco de dados
@Data   // Lombok: gera getters, setters, toString e equals/hashCode automaticamente
public class Autor {

    @Id // Define a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco gera o ID automaticamente (1, 2, 3...)
    private Long id;

    @NotBlank(message = "Nome do autor é obrigatório") // Validação: não aceita nulo nem espaços vazios
    private String nome;

    // mappedBy: indica que o mapeamento é feito pelo campo 'autor' na classe Livro.
    // Isso torna o relacionamento bidirecional.
    @OneToMany(mappedBy = "autor")
    private List<Livro> livros;
}