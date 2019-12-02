package entities;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Modalidade{
    //->Modalidades(um nome, uma lista de Escalões
    //		, um horário, uma lista de
    //		Sócios/Atletas , uma lista de Treinadores);
    @Id
    private int id;
    private String nome;
    private List<Escalao> escaloes;
//    private List<Socio> socios;


    public Modalidade(int id, String nome, List<Escalao> escaloes) {
        this.id = id;
        this.nome = nome;
        this.escaloes = escaloes;
    }

    public Modalidade() {
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

    public List<Escalao> getEscaloes() {
        return escaloes;
    }

    public void setEscaloes(List<Escalao> escaloes) {
        this.escaloes = escaloes;
    }
}
