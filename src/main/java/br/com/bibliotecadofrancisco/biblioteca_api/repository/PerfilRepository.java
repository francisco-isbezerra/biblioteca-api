package br.com.bibliotecadofrancisco.bibliotecaapi.repository;

import br.com.bibliotecadofrancisco.bibliotecaapi.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    // Repositório básico para operações de CRUD no perfil do usuário
}