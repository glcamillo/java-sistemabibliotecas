package tech.ada.sistemabiblioteca.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import tech.ada.sistemabiblioteca.model.Livro;
import tech.ada.sistemabiblioteca.model.Membro;
import tech.ada.sistemabiblioteca.service.MembroService;

import java.util.List;

@RestController
@RequestMapping("/membros")
public class MembroController {
    @Autowired
    private MembroService membroService;

    @GetMapping
    public List<Membro> getAllMembros() {
        return membroService.getAll();
    }

    @GetMapping(value = "{id}")
    public Membro getMembro(@PathVariable("id") Long id, HttpServletResponse response) {
        try {
            return membroService.getMembroById(id);
        }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Membro não encontrado");
        }
    }
    @PostMapping
    public ResponseEntity<Membro> salvarMembro(@RequestBody Membro membro){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(membroService.salvarMembro(membro));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(membro);
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> removerMembro(@PathVariable("id") Long id){
        if (this.membroService.removerMembroById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Membro> atualizarMembro(@RequestBody Membro membro){
        try {
            membroService.atualizarMembro(membro);
            return ResponseEntity.status(HttpStatus.OK).body(membro);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}