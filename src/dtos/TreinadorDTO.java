package dtos;

import entities.Atleta;
import entities.Escalao;
import entities.Horario;
import entities.Modalidade;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class TreinadorDTO extends UserDTO implements Serializable{
    private List<Modalidade> modalidades;
    private List<Escalao> escaloes;
    private List<Horario> horarios;
    private List<Atleta> atletas;

    public TreinadorDTO(String username, String name, String password, String email) {
        super(username, name, password, email);
        this.modalidades = new LinkedList<>();
        this.escaloes = new LinkedList<>();
        this.horarios = new LinkedList<>();
        this.atletas = new LinkedList<>();
    }

    public TreinadorDTO() {
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
}
