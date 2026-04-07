package br.com.bibliotecadofrancisco.bibliotecaapi.repository;

import br.com.bibliotecadofrancisco.bibliotecaapi.model.Livro;
import br.com.bibliotecadofrancisco.bibliotecaapi.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    /**
     * Filtra livros por categoria com suporte a paginação.
     */
    Page<Livro> findByCategoria(Categoria categoria, Pageable pageable);

    /**
     * Filtra livros por título (ignorando maiúsculas/minúsculas).
     */
    Page<Livro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
}