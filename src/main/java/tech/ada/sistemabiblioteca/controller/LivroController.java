package tech.ada.sistemabiblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.sistemabiblioteca.model.Livro;
import tech.ada.sistemabiblioteca.service.LivroService;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping(value = "/all")
    public List<Livro> getAllLivros() {
        return livroService.getAll();
    }

}
