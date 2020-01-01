package dtos;

import entities.Atleta;
import entities.Escalao;
import entities.Horario;
import entities.Modalidade;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class TreinadorDTO extends UserDTO implements Serializable{
    private List<ModalidadeDTO> modalidades;
    private List<EscalaoDTO> escaloes;
    private List<Horario> horarios;
    private List<AtletaDTO> atletas;

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

    public List<ModalidadeDTO> getModalidades() {
        return modalidades;
    }

    public void setModalidades(List<ModalidadeDTO> modalidades) {
        this.modalidades = modalidades;
    }

    public List<EscalaoDTO> getEscaloes() {
        return escaloes;
    }

    public void setEscaloes(List<EscalaoDTO> escaloes) {
        this.escaloes = escaloes;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public List<AtletaDTO> getAtletas() {
        return atletas;
    }

    public void setAtletas(List<AtletaDTO> atletas) {
        this.atletas = atletas;
    }
}
