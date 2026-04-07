package br.com.bibliotecadofrancisco.biblioteca_api.controller;

import br.com.bibliotecadofrancisco.bibliotecaapi.model.Livro;
import br.com.bibliotecadofrancisco.bibliotecaapi.repository.LivroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Controller responsável pelos endpoints de Livros.
 * Implementa CRUD, Paginação e HATEOAS conforme os requisitos.
 */
@RestController
@RequestMapping("/api/v1/livros")
public class LivroController {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private PagedResourcesAssembler<Livro> assembler;

    /**
     * Lista todos os livros com paginação.
     * Exemplo de chamada: /api/v1/livros?page=0&size=5
     */
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Livro>>> listarTodos(Pageable pageable) {
        // Busca os dados paginados no banco
        Page<Livro> livros = repository.findAll(pageable);

        // Converte a página em um modelo HATEOAS (com links de navegação)
        PagedModel<EntityModel<Livro>> model = assembler.toModel(livros,
                livro -> EntityModel.of(livro,
                        linkTo(methodOn(LivroController.class).buscarPorId(livro.getId())).withSelfRel(),
                        linkTo(methodOn(LivroController.class).listarTodos(null)).withRel("colecao")
                )
        );

        return ResponseEntity.ok(model);
    }

    /**
     * Busca um livro específico por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Livro>> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(livro -> EntityModel.of(livro,
                        linkTo(methodOn(LivroController.class).buscarPorId(id)).withSelfRel(),
                        linkTo(methodOn(LivroController.class).listarTodos(null)).withRel("livros")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo livro.
     * @Valid ativa as anotações Bean Validation da entidade.
     */
    @PostMapping
    public ResponseEntity<Livro> criar(@Valid @RequestBody Livro livro) {
        Livro salvo = repository.save(livro);
        return new ResponseEntity<>(salvo, HttpStatus.CREATED);
    }

    /**
     * Atualiza um livro existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @Valid @RequestBody Livro livro) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        livro.setId(id);
        return ResponseEntity.ok(repository.save(livro));
    }

    /**
     * Remove um livro do sistema.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}