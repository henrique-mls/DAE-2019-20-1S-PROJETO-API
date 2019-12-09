package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity

@NamedQueries({
        @NamedQuery(
                name = "getAllModalidades",
                query = "SELECT m FROM Modalidade m ORDER BY m.nome"
        )
})
public class Modalidade{
    //->Modalidades(um nome, uma lista de Escalões
    //		, um horário, uma lista de
    //		Sócios/Atletas , uma lista de Treinadores);
    @Id
    private int id;
    private String nome;
    @OneToMany
    private List<Horario> horarios;
    @OneToMany
    private List<Escalao> escaloes;
    @ManyToMany
    @JoinTable(name = "MODALIDADES_ATLETAS",
            joinColumns = @JoinColumn(name = "MODALIDADE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ATLETA_USERNAME", referencedColumnName =
                    "USERNAME"))
    private List<Atleta> atletas;
    @ManyToMany
    @JoinTable(name = "MODALIDADES_TREINADORES",
            joinColumns = @JoinColumn(name = "MODALIDADE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "TREINDADOR_USERNAME", referencedColumnName =
                    "USERNAME"))
    private List<Treinador> treinadores;

    public Modalidade(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.horarios = new LinkedList<>();;
        this.escaloes = new LinkedList<>();
        this.atletas = new LinkedList<>();
        this.treinadores = new LinkedList<>();
    }

    public Modalidade() {
        this.escaloes = new LinkedList<>();
        this.atletas = new LinkedList<>();
        this.treinadores = new LinkedList<>();
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

    public List<Horario> getHorario() {
        return horarios;
    }

    public void setHorario(List<Horario> horario) {
        this.horarios = horario;
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

    public void addTreinador(Treinador treinador){
        if(treinador == null || treinadores.contains(treinador)){
            return;
        }
        treinadores.add(treinador);
    }

    public void removeTreinador(Treinador treinador){
        if(treinador == null || !treinadores.contains(treinador)){
            return;
        }
        treinadores.remove(treinador);
    }

    public void addAtleta(Atleta atleta){
        if(atleta == null || atletas.contains(atleta)){
            return;
        }
        atletas.add(atleta);
    }

    public void removeAtleta(Atleta atleta){
        if(atleta == null || !atletas.contains(atleta)){
            return;
        }
        atletas.remove(atleta);
    }

    public void addEscalao(Escalao escalao){
        if(escalao == null || escaloes.contains(escalao)){
            return;
        }
        escaloes.add(escalao);
    }

    public void removeEscalao(Escalao escalao){
        if(escalao == null || !escaloes.contains(escalao)){
            return;
        }
        escaloes.remove(escalao);
    }

    public void addHorario(Horario horario){
        if(horario == null || horarios.contains(horario)){
            return;
        }
        horarios.add(horario);
    }

    public void removeHorario(Horario horario){
        if(horario == null || !horarios.contains(horario)){
            return;
        }
        horarios.remove(horario);
    }

}
