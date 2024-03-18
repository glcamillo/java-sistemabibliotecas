package tech.ada.sistemabiblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.sistemabiblioteca.model.Livro;

import java.util.Optional;

@Repository
public interface LivroFindByISBN extends JpaRepository<Livro, String> {
    Optional<Livro> findByIsbn(String isbn);
}
