package entities;

import javax.persistence.*;
import java.util.List;

@Entity
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
    @OneToMany
    private List<Horario> horarios;


    public Atleta(List<String> mensagens, List<Pagamento> pagamentos, List<Recibo> recibos,List<Modalidade> modalidades, List<Escalao> escaloes, List<Treinador> treinadores, List<Graduacao> graduacoes, List<Horario> horarios) {
        super(mensagens, pagamentos, recibos);
        this.modalidades = modalidades;
        this.escaloes = escaloes;
        this.treinadores = treinadores;
        this.graduacoes = graduacoes;
        this.horarios = horarios;
    }

    public Atleta() {
        super();
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
}
