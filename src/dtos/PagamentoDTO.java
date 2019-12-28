package dtos;

import entities.Estado;
import entities.Produto;
import entities.Recibo;
import entities.User;

import java.io.Serializable;
import java.util.Date;

public class PagamentoDTO implements Serializable {

    private int id;
    private String username;
    private int produtoID;
    private Date dataLancamento;
    private int quantidade;
    private float precoFinal;
    private Estado estado;//Pago, nao pago , parcial
    //private Recibo recibo;//caso já tenham sido pagos

    public PagamentoDTO(int id, String username, int produtoID, Date dataLancamento, int quantidade, float precoFinal, Estado estado /*,Recibo recibo*/) {
        this.id = id;
        this.username = username;
        this.produtoID = produtoID;
        this.dataLancamento = dataLancamento;
        this.quantidade = quantidade;
        this.precoFinal = precoFinal;
        this.estado = estado;
        //this.recibo = recibo;
    }

    public PagamentoDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProdutoID() {
        return produtoID;
    }

    public void setProdutoID(int produtoID) {
        this.produtoID = produtoID;
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

//    public Recibo getRecibo() {
//        return recibo;
//    }
//
//    public void setRecibo(Recibo recibo) {
//        this.recibo = recibo;
//    }
}