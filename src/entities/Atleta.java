package entities;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAtletas",
                query = "SELECT a FROM Atleta a ORDER BY a.username"
        )
})
public class Atleta extends Socio {
    //Dados pessoais, escalões,modalidade, treinadores, graduações, mensagens, horários, pagamentos e recibos
    @ManyToMany(mappedBy = "atletas")
    private List<Modalidade> modalidades;
    @OneToMany
    private List<Escalao> escaloes;
    @ManyToMany(mappedBy = "atletas")
    private List<Treinador> treinadores;
    @OneToMany
    private List<Graduacao> graduacoes;
    @ManyToMany
    @JoinTable(name = "ATLETA_HORARIOS",
            joinColumns = @JoinColumn(name = "ATLETA_USERNAME", referencedColumnName = "USERNAME"),
            inverseJoinColumns = @JoinColumn(name = "HORARIO_ID", referencedColumnName = "ID"))
    private List<Horario> horarios;


    public Atleta(String username, String name, String password, String email) {
        super(username, name, password, email);
        this.modalidades = new LinkedList<>();
        this.escaloes = new LinkedList<>();
        this.treinadores = new LinkedList<>();
        this.graduacoes = new LinkedList<>();
        this.horarios = new LinkedList<>();
    }

    public Atleta() {
        super();
        this.modalidades = new LinkedList<>();
        this.escaloes = new LinkedList<>();
        this.treinadores = new LinkedList<>();
        this.graduacoes = new LinkedList<>();
        this.horarios = new LinkedList<>();
    }

    public List<Escalao> getEscaloes() {
        return escaloes;
    }

    public void setEscaloes(List<Escalao> escaloes) {
        this.escaloes = escaloes;
    }

    public List<Graduacao> getGraduacoes() {
        return graduacoes;
    }

    public void setGraduacoes(List<Graduacao> graduacoes) {
        this.graduacoes = graduacoes;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public List<Treinador> getTreinadores() {
        return treinadores;
    }

    public void setTreinadores(List<Treinador> treinadores) {
        this.treinadores = treinadores;
    }

    public List<Modalidade> getModalidades() {
        return modalidades;
    }

    public void setModalidades(List<Modalidade> modalidades) {
        this.modalidades = modalidades;
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
}
