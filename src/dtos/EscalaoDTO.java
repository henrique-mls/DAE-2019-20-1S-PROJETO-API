package dtos;

import entities.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EscalaoDTO implements Serializable {

    private int id;
    private String nome;
    private int modalidadeID;
    private List<Horario> horarios;
    private List<AtletaDTO> atletas;
    private List<TreinadorDTO> treinadores;
    private List<PresencasDTO> presencas;


    public EscalaoDTO(int id, String nome, int modalidadeID) {
        this.id = id;
        this.nome = nome;
        this.modalidadeID = modalidadeID;
        this.atletas = new ArrayList<>();
        this.horarios = new ArrayList<>();
        this.treinadores = new ArrayList<>();
        this.presencas = new ArrayList<>();
    }

    public EscalaoDTO() {
    }

    public List<PresencasDTO> getPresencas() {
        return presencas;
    }

    public void setPresencas(List<PresencasDTO> presencas) {
        this.presencas = presencas;
    }

    public int getModalidadeID() {
        return modalidadeID;
    }

    public void setModalidadeID(int modalidadeID) {
        this.modalidadeID = modalidadeID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public List<TreinadorDTO> getTreinadores() {
        return treinadores;
    }

    public void setTreinadores(List<TreinadorDTO> treinadores) {
        this.treinadores = treinadores;
    }
}
