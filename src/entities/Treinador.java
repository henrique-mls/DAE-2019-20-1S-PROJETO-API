package entities;

import java.util.List;

public class Treinador extends User {
    //Modalidades, Escalões, Horários e Lista de Atletas das Modalidades (para saber as suas atividades no Clube)
    private List<Modalidade> modalidades;
    private List<Escalao> escaloes;
    private List<String> horarios;
    private List<Atleta> atletas;




    public Treinador(List<Modalidade> modalidades, List<Escalao> escaloes, List<String> horarios, List<Atleta> atletas) {
        this.modalidades = modalidades;
        this.escaloes = escaloes;
        this.horarios = horarios;
        this.atletas = atletas;
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
}
