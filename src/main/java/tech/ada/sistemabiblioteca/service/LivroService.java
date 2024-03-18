package tech.ada.sistemabiblioteca.service;

import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.ada.sistemabiblioteca.model.Livro;
import tech.ada.sistemabiblioteca.repository.LivroFindByISBN;
import tech.ada.sistemabiblioteca.repository.LivroRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private LivroFindByISBN livroFindByISBN;

    private LivroService(LivroRepository repository){
        this.livroRepository = repository;
    }

    public List<Livro> getAll() {
        return (List<Livro>) livroRepository.findAll();
    }

    public Livro obterLivroById(Long id) {
        return this.livroRepository.findById(id).get();
    }

    // Será verificado se ISBN de livro a incluir já não está na base
    public Livro salvarLivro(Livro livro){
        if (this.findLivroByISBN(livro)) {
            throw new RuntimeException("Livro com o mesmo valor de ISBN na base de dados.");
        }
        return this.livroRepository.save(livro);
    }

    public boolean findLivroByISBN(Livro livro){
        Optional<Livro> livroByISBN = this.livroFindByISBN.findByIsbn(livro.getIsbn());
        return (livroByISBN.isPresent()) ? true:false;
    }

    public boolean removerLivroById(Long id) {
        Optional<Livro> livro = this.livroRepository.findById(id);
        if (livro.isPresent()) {
            this.livroRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private boolean testaPresentaParametros(Livro livro){
        if (livro.getTitulo() != null && livro.getIsbn() != null && livro.getAutorNome() != null &&
            livro.getAutorSobrenome() != null && livro.getAnoPublicacao() != null &&
            livro.getEditoraNome() != null && livro.getEmprestado() != null){
            return true;
        } else{
            return false;
        }
    }

    public Livro atualizarLivro(Livro livro) {
        Livro livroParaAtualizarCriar = null;
        // ID informado -> ATUALIZA -> HTTP status 200
        if (livro.getId() != null) {
            Optional<Livro> optionalLivro = this.livroRepository.findById(livro.getId());
            if (optionalLivro.isPresent()) {
                Livro livroAlterado = optionalLivro.get();
                livroAlterado.setId(livro.getId());
                livroAlterado.setTitulo(livro.getTitulo());
                livroAlterado.setIsbn(livro.getIsbn());
                livroAlterado.setAutorNome(livro.getAutorNome());
                livroAlterado.setAutorSobrenome(livro.getAutorSobrenome());
                livroAlterado.setAnoPublicacao(livro.getAnoPublicacao());
                livroAlterado.setEditoraNome(livro.getEditoraNome());
                livroAlterado.setEmprestado(livro.getEmprestado());
                livroParaAtualizarCriar = this.livroRepository.save(livroAlterado);
            }
        // ID NÃO informado -> CRIA entidade -> HTTP status 201
        } else if (livro.getId() == null && testaPresentaParametros(livro)) {
            livroParaAtualizarCriar = salvarLivro(livro);
        } else {
            throw new RuntimeException("Livro não encontrado");
        }
        return livroParaAtualizarCriar;
    }
}