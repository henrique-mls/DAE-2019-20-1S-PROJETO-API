package entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Modalidade{
    //->Modalidades(um nome, uma lista de Escalões
    //		, um horário, uma lista de
    //		Sócios/Atletas , uma lista de Treinadores);
    @Id
    private String nome;
    private List<Escalao> escaloes;
    private List<Socio> socios;
    private String horario;
    private List<Atleta> atletas;
    private List<Treinador> treinadores;

    public Modalidade(String nome, List<Escalao> escaloes, String horario, List<Socio> socios, List<Atleta> atletas, List<Treinador> treinadores) {
        this.nome = nome;
        this.escaloes = escaloes;
        this.horario = horario;
        this.socios = new LinkedList<>();
        this.atletas = new LinkedList<>();
        this.treinadores = new LinkedList<>();
    }

    public Modalidade() {

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
