package dtos;

import entities.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class AtletaDTO extends SocioDTO implements Serializable {
    private List<Modalidade> modalidades;
    private List<Escalao> escaloes;
    private List<Treinador> treinadores;
    private List<Graduacao> graduacoes;
    private List<Horario> horarios;

    public AtletaDTO(String username, String name, String password, String email) {
        super(username, name, password, email);
        this.modalidades = new LinkedList<>();
        this.escaloes = new LinkedList<>();
        this.treinadores = new LinkedList<>();
        this.graduacoes = new LinkedList<>();
        this.horarios = new LinkedList<>();
    }

    public AtletaDTO() {
        super();
        this.modalidades = new LinkedList<>();
        this.escaloes = new LinkedList<>();
        this.treinadores = new LinkedList<>();
        this.graduacoes = new LinkedList<>();
        this.horarios = new LinkedList<>();
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

    public List<Treinador> getTreinadores() {
        return treinadores;
    }

    public void setTreinadores(List<Treinador> treinadores) {
        this.treinadores = treinadores;
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
}
