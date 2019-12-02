package entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Escalao {
    @Id
    private int id;
    private String nome;
    private Edicao edicao;

    public Escalao(int id, String nome, Edicao edicao) {
        this.id = id;
        this.nome = nome;
        this.edicao = edicao;
    }

    public Escalao() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Edicao getEdicao() {
        return edicao;
    }

    public void setEdicao(Edicao edicao) {
        this.edicao = edicao;
    }
}
