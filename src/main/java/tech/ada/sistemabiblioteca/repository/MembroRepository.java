package tech.ada.sistemabiblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.sistemabiblioteca.model.Membro;

@Repository
public interface MembroRepository extends JpaRepository<Membro, Long> {
}