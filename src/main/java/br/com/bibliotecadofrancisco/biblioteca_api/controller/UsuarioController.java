package br.com.bibliotecadofrancisco.biblioteca_api.controller;

import br.com.bibliotecadofrancisco.bibliotecaapi.model.Usuario;
import br.com.bibliotecadofrancisco.bibliotecaapi.repository.UsuarioRepository;
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
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PagedResourcesAssembler<Usuario> assembler;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Usuario>>> listarTodos(Pageable pageable) {
        Page<Usuario> usuarios = repository.findAll(pageable);
        return ResponseEntity.ok(assembler.toModel(usuarios,
                u -> EntityModel.of(u, linkTo(methodOn(UsuarioController.class).buscarPorId(u.getId())).withSelfRel())));
    }

    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario) {
        // Como Usuario tem CascadeType.ALL no Perfil, salvar o Usuario salva o Perfil automaticamente
        return new ResponseEntity<>(repository.save(usuario), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(u -> EntityModel.of(u, linkTo(methodOn(UsuarioController.class).buscarPorId(id)).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}