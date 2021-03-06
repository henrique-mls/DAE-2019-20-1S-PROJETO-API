package dtos;

import entities.Mensagem;
import entities.Pagamento;
import entities.Recibo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class SocioDTO extends UserDTO implements Serializable {
    private List<Mensagem> mensagens;
    private List<PagamentoDTO> pagamentos;
    private List<Recibo> recibos;

    public SocioDTO(String username, String name, String password, String email) {
        super(username, name, password, email);
        this.mensagens = new LinkedList<>();
        this.pagamentos = new LinkedList<>();
        this.recibos = new LinkedList<>();
    }

    public SocioDTO() {
        super();
        this.mensagens = new LinkedList<>();
        this.pagamentos = new LinkedList<>();
        this.recibos = new LinkedList<>();
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    public List<PagamentoDTO> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<PagamentoDTO> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public List<Recibo> getRecibos() {
        return recibos;
    }

    public void setRecibos(List<Recibo> recibos) {
        this.recibos = recibos;
    }
}
