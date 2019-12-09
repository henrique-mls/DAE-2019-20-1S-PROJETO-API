package entities;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Treinador extends User {
    //Modalidades, Escalões, Horários e Lista de Atletas das Modalidades (para saber as suas atividades no Clube)
    @ManyToMany(mappedBy = "treinadores")
    private List<Modalidade> modalidades;
    @OneToMany
    private List<Escalao> escaloes;

    private List<String> horarios;
    @ManyToMany
    @JoinTable(name = "TREINADOR_ATLETAS",
            joinColumns = @JoinColumn(name = "TREINADOR_USERNAME", referencedColumnName = "USERNAME"),
            inverseJoinColumns = @JoinColumn(name = "ATLETA_USERNAME", referencedColumnName =
                    "USERNAME"))
    private List<Atleta> atletas;


    public Treinador(List<Modalidade> modalidades, List<Escalao> escaloes, List<String> horarios, List<Atleta> atletas) {
        this.modalidades = modalidades;
        this.escaloes = escaloes;
        this.horarios = horarios;
        this.atletas = atletas;
    }

    public Treinador() {
        this.modalidades = new LinkedList<>();
        this.escaloes = new LinkedList<>();
        this.horarios = new LinkedList<>();
        this.atletas = new LinkedList<>();
    }

    public List<Modalidade> getModalidades() {
        return modalidades;
    }

    public void setModalidades(List<Modalidade> modalidades) {
        this.modalidades = modalidades;
    }

    public List<Escalao> getEscaloes() {
        return escaloes;
    }

    public void setEscaloes(List<Escalao> escaloes) {
        this.escaloes = escaloes;
    }

    public List<String> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<String> horarios) {
        this.horarios = horarios;
    }

    public List<Atleta> getAtletas() {
        return atletas;
    }

    public void setAtletas(List<Atleta> atletas) {
        this.atletas = atletas;
    }

    public void addModalidade(Modalidade modalidade){
        if(modalidade == null || modalidades.contains(modalidade)){
            return;
        }
        modalidades.add(modalidade);
    }

    public void removeModalidade(Modalidade modalidade){
        if(modalidade == null || !modalidades.contains(modalidade)){
            return;
        }
        modalidades.remove(modalidade);
    }
}
