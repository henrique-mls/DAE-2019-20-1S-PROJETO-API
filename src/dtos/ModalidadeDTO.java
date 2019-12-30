package dtos;

import entities.Atleta;
import entities.Escalao;
import entities.Horario;
import entities.Treinador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModalidadeDTO implements Serializable {

    private int id;
    private String nome;
    private List<Escalao> escaloes;
    private List<AtletaDTO> atletas;
    private List<TreinadorDTO> treinadores;
    private List<Horario> horarios;

    public ModalidadeDTO(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.horarios = new ArrayList<>();
        this.treinadores = new ArrayList<>();
        this.atletas = new ArrayList<>();
        this.escaloes = new ArrayList<>();
    }

    public ModalidadeDTO() {
        this.horarios = new ArrayList<>();
        this.treinadores = new ArrayList<>();
        this.atletas = new ArrayList<>();
        this.escaloes = new ArrayList<>();
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

    public List<AtletaDTO> getAtletas() {
        return atletas;
    }

    public void setAtletas(List<AtletaDTO> atletas) {
        this.atletas = atletas;
    }

    public List<TreinadorDTO> getTreinadores() {
        return treinadores;
    }

    public void setTreinadores(List<TreinadorDTO> treinadores) {
        this.treinadores = treinadores;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }
}
