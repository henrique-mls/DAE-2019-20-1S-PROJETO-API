package entities;

public class Escalao {
    private String edicao;
    private String horario;

    public Escalao(String edicao, String horario) {
        this.edicao = edicao;
        this.horario = horario;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
