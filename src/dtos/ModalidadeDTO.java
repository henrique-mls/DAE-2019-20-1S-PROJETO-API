package dtos;

import entities.Atleta;
import entities.Escalao;
import entities.Horario;
import entities.Treinador;

import java.io.Serializable;
import java.util.List;

public class ModalidadeDTO implements Serializable {

    private int id;
    private String nome;
    private List<Escalao> escaloes;
    private List<Atleta> atletas;
    private List<Treinador> treinadores;
    private Horario horario;

    public ModalidadeDTO(int id, String nome ,Horario horario) {
        this.id = id;
        this.nome = nome;
        this.horario = horario;
    }

    public ModalidadeDTO() {
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

    public List<Atleta> getAtletas() {
        return atletas;
    }

    public void setAtletas(List<Atleta> atletas) {
        this.atletas = atletas;
    }

    public List<Treinador> getTreinadores() {
        return treinadores;
    }

    public void setTreinadores(List<Treinador> treinadores) {
        this.treinadores = treinadores;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }
}
