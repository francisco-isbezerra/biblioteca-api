package br.com.bibliotecadofrancisco.biblioteca_api.controller;

import br.com.bibliotecadofrancisco.bibliotecaapi.model.Emprestimo;
import br.com.bibliotecadofrancisco.biblioteca_api.repository.EmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoRepository repository;

    @Autowired
    private PagedResourcesAssembler<Emprestimo> assembler;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Emprestimo>>> listarTodos(Pageable pageable) {
        Page<Emprestimo> emprestimos = repository.findAll(pageable);
        return ResponseEntity.ok(assembler.toModel(emprestimos,
                e -> EntityModel.of(e, linkTo(methodOn(EmprestimoController.class).buscarPorId(e.getId())).withSelfRel())));
    }

    @PostMapping
    public ResponseEntity<Emprestimo> realizarEmprestimo(@RequestBody Emprestimo emprestimo) {
        emprestimo.setDataEmprestimo(LocalDate.now()); // Define a data atual automaticamente
        return new ResponseEntity<>(repository.save(emprestimo), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Emprestimo>> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(e -> EntityModel.of(e, linkTo(methodOn(EmprestimoController.class).buscarPorId(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}