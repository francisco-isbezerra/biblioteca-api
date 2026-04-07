package br.com.bibliotecadofrancisco.bibliotecaapi.repository;

import br.com.bibliotecadofrancisco.bibliotecaapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca usuário pelo e-mail exato.
     */
    Page<Usuario> findByEmail(String email, Pageable pageable);
}