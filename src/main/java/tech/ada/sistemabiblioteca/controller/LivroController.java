package tech.ada.sistemabiblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

import tech.ada.sistemabiblioteca.model.Livro;
import tech.ada.sistemabiblioteca.service.LivroService;



@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public List<Livro> getAllLivros() {
        return livroService.getAll();
    }

    @GetMapping(value = "{id}")
    public Livro obterLivro(@PathVariable("id") Long id) {
        try {
            return this.livroService.obterLivroById(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Livro não encontrado");
        }
    }
    @PostMapping
    public ResponseEntity<Livro> salvarLivro(@RequestBody Livro livro){
        try {
            livroService.salvarLivro(livro);
            return ResponseEntity.status(HttpStatus.CREATED).body(livro);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(livro);
        }
    }
    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> removerLivro(@PathVariable("id") Long id){
        if (this.livroService.removerLivroById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping
    public ResponseEntity<Livro> atualizarLivro(@RequestBody Livro livro){
        try {
            livroService.atualizarLivro(livro);
            return ResponseEntity.status(HttpStatus.OK).body(livro);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
