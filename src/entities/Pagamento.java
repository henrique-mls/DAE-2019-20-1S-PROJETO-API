package entities;

import java.util.Date;

public class Pagamento {

    //Os pagamentos deverão estar associados a determinado
    //utilizador, e referenciam-se a um Produto, têm uma data de lançamento, quantidade, um
    //preço final, um estado (pago, não pago, parcial) e um recibo associado (caso já tenham
    //sido pagos). Deverá ser possível gerar um documento PDF estilo recibo que engloba um
    //ou vários Pagamentos;

    private User utilizador;
    private Produto produto;
    private Date data_lancamento;
    private int quantidade;
    private float preco_final;
    private Estado estado;//Pago, nao pago , parcial
    private Recibo recibo;

    public Pagamento(User utilizador, Produto produto, Date data_lancamento, int quantidade, float preco_final, Estado estado, Recibo recibo) {
        this.utilizador = utilizador;
        this.produto = produto;
        this.data_lancamento = data_lancamento;
        this.quantidade = quantidade;
        this.preco_final = preco_final;
        this.estado = estado;
        this.recibo = recibo;
    }

    public Recibo getRecibo() {
        return recibo;
    }

    public void setRecibo(Recibo recibo) {
        this.recibo = recibo;
    }

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

    public Date getData_lancamento() {
        return data_lancamento;
    }

    public void setData_lancamento(Date data_lancamento) {
        this.data_lancamento = data_lancamento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getPreco_final() {
        return preco_final;
    }

    public void setPreco_final(float preco_final) {
        this.preco_final = preco_final;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
