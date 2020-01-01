package entities;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.LinkedList;
import java.util.List;

@Entity

@NamedQueries({
        @NamedQuery(
                name = "getAllTreinadores",
                query = "SELECT t FROM Treinador t ORDER BY t.username"
        )
})
public class Treinador extends User {
    //Modalidades, Escalões, Horários e Lista de Atletas das Modalidades (para saber as suas atividades no Clube)
    @ManyToMany(mappedBy = "treinadores")
    private List<Modalidade> modalidades;
    @ManyToMany(mappedBy = "treinadores")
    private List<Escalao> escaloes;
    @ManyToMany
    @JoinTable(name = "TREINADOR_HORARIOS",
            joinColumns = @JoinColumn(name = "TREINADOR_USERNAME", referencedColumnName = "USERNAME"),
            inverseJoinColumns = @JoinColumn(name = "HORARIO_ID", referencedColumnName = "ID"))
    private List<Horario> horarios;
    @ManyToMany
    @JoinTable(name = "TREINADOR_ATLETAS",
            joinColumns = @JoinColumn(name = "TREINADOR_USERNAME", referencedColumnName = "USERNAME"),
            inverseJoinColumns = @JoinColumn(name = "ATLETA_USERNAME", referencedColumnName = "USERNAME"))
    private List<Atleta> atletas;


    public Treinador(String username, String name, String password, String email) {
        super(username, name, password, email);
        this.modalidades = new LinkedList<>();
        this.escaloes = new LinkedList<>();
        this.horarios = new LinkedList<>();
        this.atletas = new LinkedList<>();
    }

    public Treinador() {
        super();
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

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
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
}
