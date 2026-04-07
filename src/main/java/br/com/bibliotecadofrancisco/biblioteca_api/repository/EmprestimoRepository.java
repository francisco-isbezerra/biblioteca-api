package br.com.bibliotecadofrancisco.biblioteca_api.repository;

import br.com.bibliotecadofrancisco.bibliotecaapi.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    /**
     * Busca empréstimos realizados em uma data específica.
     */
    Page<Emprestimo> findByDataEmprestimo(LocalDate data, Pageable pageable);
}