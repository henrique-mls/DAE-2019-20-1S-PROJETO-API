package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.List;

@Entity
public class Atleta extends Socio {

    //Dados pessoais, escalões,modalidade, treinadores, graduações, mensagens, horários, pagamentos e recibos
    private Modalidade modalidade;
    private List<Escalao> escaloes;
    private List<Treinador> treinadores;
    private List<String> graduacoes;
    private List<String> horarios;


    public Atleta(List<String> mensagens, List<Pagamento> pagamentos, List<Recibo> recibos, Modalidade modalidade, List<Escalao> escaloes, List<Treinador> treinadores, List<String> graduacoes, List<String> horarios) {
        super(mensagens, pagamentos, recibos);
        this.modalidade = modalidade;
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

    public List<String> getGraduacoes() {
        return graduacoes;
    }

    public void setGraduacoes(List<String> graduacoes) {
        this.graduacoes = graduacoes;
    }

    public List<String> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<String> horarios) {
        this.horarios = horarios;
    }

    public List<Treinador> getTreinadores() {
        return treinadores;
    }

    public void setTreinadores(List<Treinador> treinadores) {
        this.treinadores = treinadores;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }
}
