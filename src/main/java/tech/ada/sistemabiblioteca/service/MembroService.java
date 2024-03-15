package tech.ada.sistemabiblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.ada.sistemabiblioteca.model.Livro;
import tech.ada.sistemabiblioteca.model.Membro;
import tech.ada.sistemabiblioteca.repository.MembroRepository;

import java.math.BigDecimal;
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

    // Criação de um novo objeto (newMembro) para ajustar valor de multa acumulada, que não
    //   deve ser informada na requisição. Decisão de projeto.
    public Membro salvarMembro(Membro membro) {
        Membro newMembro = new Membro();
        newMembro.setNome(membro.getNome());
        newMembro.setSobrenome(membro.getSobrenome());
        newMembro.setContato(membro.getContato());
        newMembro.setEndereco(membro.getEndereco());
        newMembro.setMultaAcumulada(BigDecimal.valueOf(0.0));
        return this.membroRepository.save(newMembro);
    }
}