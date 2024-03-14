package tech.ada.sistemabiblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ada.sistemabiblioteca.model.Livro;
import tech.ada.sistemabiblioteca.repository.LivroRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    private LivroService(LivroRepository repository){
        this.livroRepository = repository;
    }

    public List<Livro> getAll() {
        return (List<Livro>) livroRepository.findAll();
    }
}
