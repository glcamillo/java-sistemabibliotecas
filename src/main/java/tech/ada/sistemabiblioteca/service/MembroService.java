package tech.ada.sistemabiblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.ada.sistemabiblioteca.model.Livro;
import tech.ada.sistemabiblioteca.model.Membro;
import tech.ada.sistemabiblioteca.repository.MembroRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MembroService {

    @Autowired
    private MembroRepository membroRepository;

    private MembroService(MembroRepository repository){
        this.membroRepository = repository;
    }

    public List<Membro> getAll() {
        return (List<Membro>) membroRepository.findAll();
    }

    public Membro getMembroById(Long id) {
        return this.membroRepository.findById(id).get();
    }
    public Membro salvarMembro(Membro membro){
        return this.membroRepository.save(membro);
    }

}