package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity

@NamedQueries({
        @NamedQuery(
                name = "getAllProdutos",
                query = "SELECT p FROM Produto p ORDER BY p.id"
        )
})

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
    @OneToMany(mappedBy = "produto",cascade = CascadeType.REMOVE)
    private List<Pagamento> pagamentos;

    public Produto() {

    }

    public Produto(int id, TipoProduto tipo, String descricao, float valorBase) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valorBase = valorBase;
        this.pagamentos = new ArrayList<>();
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public void addPagamento(Pagamento pagamento){
        if(pagamento == null || pagamentos.contains(pagamento)){
            return;
        }
        pagamentos.add(pagamento);
    }

    public void removePagamentos(Pagamento pagamento){
        if(pagamento == null || !pagamentos.contains(pagamento)){
            return;
        }
        pagamentos.remove(pagamento);
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
