package br.com.bibliotecadofrancisco.bibliotecaapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Email(message = "E-mail deve ser válido") // Valida o formato do e-mail (ex@ex.com)
    private String email;

    // OneToOne: Um usuário tem apenas um perfil.
    // CascadeType.ALL: Se eu salvar/deletar o Usuário, o Perfil dele sofre a mesma ação.
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "perfil_id") // Cria uma chave estrangeira para o perfil
    private Perfil perfil;
}