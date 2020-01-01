package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity

@NamedQueries({
        @NamedQuery(
                name = "getAllModalidades",
                query = "SELECT m FROM Modalidade m ORDER BY m.nome"
        )
})
public class Modalidade{
    //->Modalidades(um nome, uma lista de Escalões
    //		, um horário, uma lista de
    //		Sócios/Atletas , uma lista de Treinadores);
    @Id
    private int id;
    private String nome;
    @OneToMany(mappedBy = "modalidade", cascade = CascadeType.REMOVE)
    private List<Escalao> escaloes;
    @ManyToMany
    @JoinTable(name = "MODALIDADE_ATLETAS",
            joinColumns = @JoinColumn(name = "MODALIDADE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ATLETA_USERNAME", referencedColumnName =
                    "USERNAME"))
    private List<Atleta> atletas;
    @ManyToMany
    @JoinTable(name = "MODALIDADE_TREINADORES",
            joinColumns = @JoinColumn(name = "MODALIDADE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "TREINADOR_USERNAME", referencedColumnName =
                    "USERNAME"))
    private List<Treinador> treinadores;


    public Modalidade(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.escaloes = new LinkedList<>();
        this.treinadores = new LinkedList<>();
        this.atletas = new LinkedList<>();
    }

    public Modalidade() {
        this.escaloes = new LinkedList<>();
        this.treinadores = new LinkedList<>();
        this.atletas = new LinkedList<>();
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

    public List<Escalao> getEscaloes() {
        return escaloes;
    }

    public void setEscaloes(List<Escalao> escaloes) {
        this.escaloes = escaloes;
    }

    public void addEscalao(Escalao escalao){
        if(escalao == null || escaloes.contains(escalao)){
            return;
        }
        escaloes.add(escalao);
    }

    public void removeEscalao(Escalao escalao){
        if(escalao == null || !escaloes.contains(escalao)){
            return;
        }
        escaloes.remove(escalao);
    }
}
