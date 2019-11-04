package entities;

public class Product {
    //->Produto(um tipo, descrição , um valor base associados)
    //
    public ProductType tipo;
    public String descricao;
    public float valorBase;

    public Product(ProductType tipo, String descricao, float valorBase) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.valorBase = valorBase;
    }

    public ProductType getTipo() {
        return tipo;
    }

    public void setTipo(ProductType tipo) {
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
