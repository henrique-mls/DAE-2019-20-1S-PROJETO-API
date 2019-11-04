package entities;

import java.util.List;

public class Modality {
    //->Modalidades(um nome, uma lista de Escalões
    //		, um horário, uma lista de
    //		Sócios/Atletas , uma lista de Treinadores);
    public String nome;
    //ublic List<Escalao> escaloes;
    //public Horario horario;
    public List<Partner> partners;
    public List<Coach> treinadores;
}
