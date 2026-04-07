package br.com.bibliotecadofrancisco.biblioteca_api.controller;
import br.com.bibliotecadofrancisco.bibliotecaapi.model.Autor;
import br.com.bibliotecadofrancisco.biblioteca_api.repository.AutorRepository;
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
public class AutorController {

    @Autowired
    private AutorRepository repository;

    @Autowired
    private PagedResourcesAssembler<Autor> assembler;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Autor>>> listarTodos(Pageable pageable) {
        Page<Autor> autores = repository.findAll(pageable);
        // Converte a lista paginada em modelo HATEOAS
        return ResponseEntity.ok(assembler.toModel(autores,
                autor -> EntityModel.of(autor,
                        linkTo(methodOn(AutorController.class).buscarPorId(autor.getId())).withSelfRel())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Autor>> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(autor -> EntityModel.of(autor,
                        linkTo(methodOn(AutorController.class).buscarPorId(id)).withSelfRel(),
                        linkTo(methodOn(AutorController.class).listarTodos(null)).withRel("todos-autores")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Autor> criar(@Valid @RequestBody Autor autor) {
        return new ResponseEntity<>(repository.save(autor), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}