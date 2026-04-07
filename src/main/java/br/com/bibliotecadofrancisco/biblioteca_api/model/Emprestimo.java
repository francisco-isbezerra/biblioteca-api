package br.com.bibliotecadofrancisco.bibliotecaapi.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataEmprestimo;

    // Muitos empréstimos podem ser feitos por um Usuário
    @ManyToOne
    private Usuario usuario;

    // Muitos empréstimos podem envolver um Livro
    @ManyToOne
    private Livro livro;

    /**
     *
     * Por que não usar @ManyToMany direto entre Livro e Usuario?
     *  Porque em um sistema real precisamos de campos extras (como dataEmprestimo),
     * e o JPA recomenda criar uma entidade intermediária para ter mais controle.
     */
}