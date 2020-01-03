package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "getAllGraduacoes", query = "SELECT g FROM Graduacao g order by g.id"),
        @NamedQuery(name = "getGraduacao", query = "SELECT g FROM Graduacao g WHERE g.id = :id")
})

public class Graduacao implements Serializable {
    @Id
    private int id;
    private String nome;
    private String descricao;

    public Graduacao(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Graduacao() {

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
