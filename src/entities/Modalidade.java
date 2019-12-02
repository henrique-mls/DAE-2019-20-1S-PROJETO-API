package entities;

import java.util.LinkedList;
import java.util.List;

public class Modalidade{
    //->Modalidades(um nome, uma lista de Escalões
    //		, um horário, uma lista de
    //		Sócios/Atletas , uma lista de Treinadores);
    public String nome;
    public List<String> escaloes;
    public String horario;
    public List<Socio> socios;
    public List<Atleta> atletas;
    public List<Treinador> treinadores;

    public Modalidade(String nome, List<String> escaloes, String horario, List<Socio> socios, List<Atleta> atletas, List<Treinador> treinadores) {
        this.nome = nome;
        this.escaloes = escaloes;
        this.horario = horario;
        this.socios = new LinkedList<>();
        this.atletas = new LinkedList<>();
        this.treinadores = new LinkedList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getEscaloes() {
        return escaloes;
    }

    public void setEscaloes(List<String> escaloes) {
        this.escaloes = escaloes;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public List<Socio> getSocios() {
        return socios;
    }

    public void setSocios(List<Socio> socios) {
        this.socios = socios;
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
}
