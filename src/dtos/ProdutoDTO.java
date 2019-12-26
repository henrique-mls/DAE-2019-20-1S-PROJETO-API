package dtos;

import entities.TipoProduto;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ProdutoDTO implements Serializable {

    private int id;
    private TipoProduto tipo;
    private String descricao;
    private float valorBase;

    public ProdutoDTO() {

    }

    public ProdutoDTO(int id, TipoProduto tipo, String descricao, float valorBase) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valorBase = valorBase;
    }

    public ProdutoDTO(TipoProduto tipo, String descricao, float valorBase) {
        this(-1,tipo,descricao,valorBase);
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
