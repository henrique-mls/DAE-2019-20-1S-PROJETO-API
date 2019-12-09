package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.DayOfWeek;

@Entity
public class Horario {

    @Id
    private int id;
    private DayOfWeek dia;
    private int horaInicio;
    private int duracao;


    public Horario(int id, DayOfWeek dia, int horaInicio, int duracao) {
        this.id = id;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.duracao = duracao;
    }

    public Horario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DayOfWeek getDia() {
        return dia;
    }

    public void setDia(DayOfWeek dia) {
        this.dia = dia;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
}
