package br.com.bibliotecadofrancisco.biblioteca_api.repository;

import br.com.bibliotecadofrancisco.bibliotecaapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository // Define que esta interface gerencia a persistência da entidade Autor
public interface AutorRepository extends JpaRepository<Autor, Long> {

    /**
     * Consulta personalizada para buscar autores pelo nome (parcial).
     * O Spring Data JPA cria a query automaticamente pelo nome do método.
     */
    Page<Autor> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}