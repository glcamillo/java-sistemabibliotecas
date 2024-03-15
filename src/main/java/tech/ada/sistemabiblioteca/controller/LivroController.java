package tech.ada.sistemabiblioteca.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tech.ada.sistemabiblioteca.model.Livro;
import tech.ada.sistemabiblioteca.service.LivroService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping(value = "/all")
    public List<Livro> getAllLivros() {
        return livroService.getAll();
    }

    @GetMapping(value = "/livro/{id}")
    public Livro getLivro(@PathVariable("id") Long id) {
        try {
            return this.livroService.getLivroById(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado");
        }
    }
    @PostMapping(value = "/livro")
    public ResponseEntity<Livro> salvarLivro(@RequestBody Livro livro){
        try {
            livroService.salvarLivro(livro);
            return ResponseEntity.status(HttpStatus.CREATED).body(livro);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(livro);
        }
    }

}
