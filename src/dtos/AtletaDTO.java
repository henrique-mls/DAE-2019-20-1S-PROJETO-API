package dtos;

import entities.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class AtletaDTO extends SocioDTO implements Serializable {
    private List<ModalidadeDTO> modalidades;
    private List<EscalaoDTO> escaloes;
    private List<TreinadorDTO> treinadores;
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

    public List<TreinadorDTO> getTreinadores() {
        return treinadores;
    }

    public void setTreinadores(List<TreinadorDTO> treinadores) {
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
