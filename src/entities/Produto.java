package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Produto implements Serializable {
    //->Produto(um tipo, descrição , um valor base associados)
    //
    @Id
    private int id;
    @NotNull
    private TipoProduto tipo;
    private String descricao;
    @NotNull
    private float valorBase;

    public Produto() {

    }

    public Produto(int id, TipoProduto tipo, String descricao, float valorBase) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valorBase = valorBase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoProduto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProduto tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValorBase() {
        return valorBase;
    }

    public void setValorBase(float valorBase) {
        this.valorBase = valorBase;
    }
}
