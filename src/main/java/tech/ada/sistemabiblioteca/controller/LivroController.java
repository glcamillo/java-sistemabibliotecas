package tech.ada.sistemabiblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Objects;

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
    // Se requisição PUT contiver corpo com representação de livro:
    // a) com id na base e update com sucesso, então, retorna 200 (OK)
    // b) sem id na base e é possível criar entidade, então, retorna 201 (CREATED)
    //       Mesmo resultado de POST.
    @PutMapping
    public ResponseEntity<Livro> atualizarLivro(@RequestBody Livro livro){
        try {
            Long idFromRequest = livro.getId();
            Livro livroParaAtualizar = livroService.atualizarLivro(livro);
            if (livroParaAtualizar.getId() != null && (Objects.equals(livroParaAtualizar.getId(), idFromRequest))) {
                // Requisição COM ID: representação atualizada: HTTP status 200
                return ResponseEntity.status(HttpStatus.OK).body(livroParaAtualizar);
            } else {
                // Requisição SEM ID: representação persistida: HTTP status 201
                return ResponseEntity.status(HttpStatus.CREATED).body(livroParaAtualizar);
            }
        } catch (Exception ex) {
            // Exceção gerada por: campos faltantes ou conflito de ISBN
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}