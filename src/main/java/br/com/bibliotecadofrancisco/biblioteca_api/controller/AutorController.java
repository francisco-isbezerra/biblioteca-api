package br.com.bibliotecadofrancisco.biblioteca_api.controller;

import br.com.bibliotecadofrancisco.biblioteca_api.model.Autor;
import br.com.bibliotecadofrancisco.biblioteca_api.repository.AutorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/api/v1/autores")
@Tag(name = "Autores", description = "Endpoints para gerenciamento de autores")
public class AutorController {

    @Autowired
    private AutorRepository repository;

    @Autowired
    private PagedResourcesAssembler<Autor> assembler;

    @Operation(summary = "Listar todos os autores", description = "Retorna uma lista paginada de autores com links HATEOAS")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Autor>>> listarTodos(Pageable pageable) {
        Page<Autor> autores = repository.findAll(pageable);
        return ResponseEntity.ok(assembler.toModel(autores,
                autor -> EntityModel.of(autor,
                        linkTo(methodOn(AutorController.class).buscarPorId(autor.getId())).withSelfRel())));
    }

    @Operation(summary = "Buscar autor por ID", description = "Retorna um único autor com base no ID fornecido")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Autor>> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(autor -> EntityModel.of(autor,
                        linkTo(methodOn(AutorController.class).buscarPorId(id)).withSelfRel(),
                        linkTo(methodOn(AutorController.class).listarTodos(null)).withRel("todos-autores")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Criar novo autor", description = "Cadastra um novo autor no sistema")
    @PostMapping
    public ResponseEntity<Autor> criar(@Valid @RequestBody Autor autor) {
        return new ResponseEntity<>(repository.save(autor), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar autor", description = "Atualiza os dados de um autor existente")
    @PutMapping("/{id}")
    public ResponseEntity<Autor> atualizar(@PathVariable Long id, @Valid @RequestBody Autor autorAtualizado) {
        return repository.findById(id)
                .map(autor -> {
                    autor.setNome(autorAtualizado.getNome());
                    // Adicione outros sets se o modelo Autor tiver mais campos
                    return ResponseEntity.ok(repository.save(autor));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar autor", description = "Remove um autor do sistema permanentemente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}