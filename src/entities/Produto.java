package entities;

public class Produto {
    //->Produto(um tipo, descrição , um valor base associados)
    //
    private TipoProduto tipo;
    private String descricao;
    private float valorBase;

    public Produto(TipoProduto tipo, String descricao, float valorBase) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.valorBase = valorBase;
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
