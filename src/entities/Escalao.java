package entities;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllEscaloes",
                query = "SELECT e FROM Escalao e ORDER BY e.id"
        )
})
public class Escalao {
    @Id
    private int id;
    private String nome;
    @OneToMany
    private List<Horario> horarios;
    @ManyToMany
    @JoinTable(name = "ESCALAO_ATLETAS",
            joinColumns = @JoinColumn(name = "ESCALAO_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ATLETA_USERNAME", referencedColumnName =
                    "USERNAME"))
    private List<Atleta> atletas;
    @ManyToMany
    @JoinTable(name = "ESCALAO_TREINADORES",
            joinColumns = @JoinColumn(name = "ESCALAO_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "TREINADOR_USERNAME", referencedColumnName =
                    "USERNAME"))
    private List<Treinador> treinadores;
    @ManyToOne
    private Modalidade modalidade;

    public Escalao(int id, String nome,Modalidade modalidade) {
        this.id = id;
        this.nome = nome;
        this.modalidade = modalidade;
        this.horarios = new ArrayList<>();
        this.treinadores = new ArrayList<>();
        this.atletas = new ArrayList<>();
    }

    public Escalao() {
        this.horarios = new ArrayList<>();
        this.treinadores = new ArrayList<>();
        this.atletas = new ArrayList<>();
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
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

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
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

    public void invalidateModalidade(){
        this.modalidade = null;
    }
}
