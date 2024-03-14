package tech.ada.sistemabiblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}