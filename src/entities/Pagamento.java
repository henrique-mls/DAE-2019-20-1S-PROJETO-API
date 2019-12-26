package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NamedQueries({
        @NamedQuery(
                name = "getAllPagamentos",
                query = "SELECT p FROM Pagamento p ORDER BY p.id"
        )
})

@Entity
public class Pagamento {

    //Os pagamentos deverão estar associados a determinado
    //utilizador, e referenciam-se a um Produto, têm uma data de lançamento, quantidade, um
    //preço final, um estado (pago, não pago, parcial) e um recibo associado (caso já tenham
    //sido pagos). Deverá ser possível gerar um documento PDF estilo recibo que engloba um
    //ou vários Pagamentos;
    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "USERNAME")
    @NotNull
    private User utilizador;
    @ManyToOne
    @JoinColumn(name = "PRODUTO_ID")
    @NotNull
    private Produto produto;
    private Date dataLancamento;
    private int quantidade;
    private float precoFinal;
    private Estado estado;//Pago, nao pago , parcial
//    @ManyToOne
//    @JoinColumn(name = "RECIBO_ID")
//    @NotNull
//    private Recibo recibo;//caso já tenham sido pagos

    public Pagamento(int id, User utilizador, Produto produto, Date dataLancamento, int quantidade, float precoFinal, Estado estado/*, Recibo recibo*/) {
        this.utilizador = utilizador;
        this.produto = produto;
        this.dataLancamento = dataLancamento;
        this.quantidade = quantidade;
        this.precoFinal = precoFinal;
        this.estado = estado;
        //this.recibo = recibo;
    }

    public Pagamento() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public Recibo getRecibo() {
//        return recibo;
//    }
//
//    public void setRecibo(Recibo recibo) {
//        this.recibo = recibo;
//    }

    public User getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(User utilizador) {
        this.utilizador = utilizador;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(float precoFinal) {
        this.precoFinal = precoFinal;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
