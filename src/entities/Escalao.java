package entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Escalao {
    @Id
    private int id;
    private String nome;

    public Escalao(int id, String nome) {
        this.id = id;
        this.nome = nome;
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
}
