package tech.ada.sistemabiblioteca.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_membros")
public class Membro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String sobrenome;
    private String endereco;
    private String contato;

    @Column(name = "multa_acumulada")
    private BigDecimal multaAcumulada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public BigDecimal getMultaAcumulada() {
        return multaAcumulada;
    }
    public void setMultaAcumulada() {
        this.multaAcumulada = BigDecimal.valueOf(0.0);
    }

    public void setMultaAcumulada(BigDecimal multaAcumulada) {
        this.multaAcumulada = multaAcumulada;
    }
}
