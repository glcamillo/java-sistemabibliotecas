package tech.ada.sistemabiblioteca.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_emprestimos")
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "membro_id")
    /*
        @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;
     */
    private Long membroId;
    @Column(name = "livro_id")
    private Long livroId;
    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;
    @Column(name = "valor_multa_dia")
    private BigDecimal valorMultaDia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMembroId() {
        return membroId;
    }

    public void setMembroId(Long membroId) {
        this.membroId = membroId;
    }

    public Long getLivroId() {
        return livroId;
    }

    public void setLivroId(Long livroId) {
        this.livroId = livroId;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public BigDecimal getValorMultaDia() {
        return valorMultaDia;
    }

    public void setValorMultaDia(BigDecimal valorMultaDia) {
        this.valorMultaDia = valorMultaDia;
    }
}
