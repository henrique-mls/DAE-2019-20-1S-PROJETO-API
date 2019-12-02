package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Edicao {
    @Id
    private int id;
    private String edicao;
    private String horario;
    private List<Atleta> atletas;
    //    @ManyToMany
//    @JoinTable(name = "MODALIDADES_TREINADORES",
//            joinColumns = @JoinColumn(name = "modalidade_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "TREINADOR_USERNAME", referencedColumnName =
//                    "USERNAME"))
    private List<Treinador> treinadores;

    public Edicao(int id, String edicao, String horario, List<Atleta> atletas, List<Treinador> treinadores) {
        this.id = id;
        this.edicao = edicao;
        this.horario = horario;
        this.atletas = atletas;
        this.treinadores = treinadores;
    }

    public Edicao() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Atleta> getAtletas() {
        return atletas;
    }

    public void setAtletas(List<Atleta> atletas) {
        this.atletas = atletas;
    }

    public List<Treinador> getTreinadores() {
        return treinadores;
    }

    public void setTreinadores(List<Treinador> treinadores) {
        this.treinadores = treinadores;
    }
}
