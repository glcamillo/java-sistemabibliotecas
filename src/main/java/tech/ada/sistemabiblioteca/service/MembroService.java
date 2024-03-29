package tech.ada.sistemabiblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.MethodArgumentNotValidException;
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

    public boolean removerMembroById(Long id) {
        Optional<Membro> membro = this.membroRepository.findById(id);
        if (membro.isPresent()) {
            this.membroRepository.deleteById(id);
            return true;
        }
        return false;
    }
    // Checa se todos os parâmetros para incluir novo membro foram passados
    private boolean testaPresentaParametros(Membro membro){
        if (membro.getNome() != null && membro.getSobrenome() != null &&
                membro.getContato() != null && membro.getEndereco() != null &&
                membro.getMultaAcumulada()!= null){
            return true;
        } else{
            return false;
        }
    }

    public Membro atualizarMembro(Membro membro) {
        Membro membroParaAtualizarCriar = null;
        // ID informado -> ATUALIZA -> HTTP status 200
        if (membro.getId() != null) {
            Optional<Membro> optionalMembro = this.membroRepository.findById(membro.getId());
            if (optionalMembro.isPresent()) {
                Membro membroAlterado = optionalMembro.get();
                membroAlterado.setNome(membro.getNome());
                membroAlterado.setSobrenome(membro.getSobrenome());
                membroAlterado.setEndereco(membro.getEndereco());
                membroAlterado.setContato(membro.getContato());
                membroAlterado.setMultaAcumulada(membro.getMultaAcumulada());

                membroParaAtualizarCriar = this.membroRepository.save(membroAlterado);
            }
            // ID NÃO informado -> CRIA entidade -> HTTP status 201
        } else if (membro.getId() == null && testaPresentaParametros(membro)) {
            membroParaAtualizarCriar = salvarMembro(membro);
        } else {
            throw new RuntimeException();
        }
        return membroParaAtualizarCriar;
    }
}
