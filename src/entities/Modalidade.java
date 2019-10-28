package entities;

import java.util.List;

public class Modalidade {
    //->Modalidades(um nome, uma lista de Escalões
    //		, um horário, uma lista de
    //		Sócios/Atletas , uma lista de Treinadores);
    public String nome;
    //ublic List<Escalao> escaloes;
    //public Horario horario;
    public List<Socio> socios;
    public List<Treinador> treinadores;
}
