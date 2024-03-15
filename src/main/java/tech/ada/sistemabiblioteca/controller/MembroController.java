package tech.ada.sistemabiblioteca.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import tech.ada.sistemabiblioteca.model.Membro;
import tech.ada.sistemabiblioteca.service.MembroService;

import java.util.List;

@RestController
@RequestMapping("/membros")
public class MembroController {
    @Autowired
    private MembroService membroService;

    @GetMapping(value = "/all")
    public List<Membro> getAllMembros() {
        return membroService.getAll();
    }

    @GetMapping(value = "/membro/{id}")
    public Membro getMembro(@PathVariable("id") Long id, HttpServletResponse response) {
        try {
            return membroService.getMembroById(id);
        }
        catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Membro não encontrado");
        }
    }
    @PostMapping(value = "/membro")
    public ResponseEntity<Membro> salvarMembro(@RequestBody Membro membro){
        try {
            membroService.salvarMembro(membro);
            return ResponseEntity.ok(membro);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(membro);
        }
    }
}