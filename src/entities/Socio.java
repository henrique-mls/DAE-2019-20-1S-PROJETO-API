package entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Socio extends User {
    private List<String> mensagens;
    private List<Pagamento> pagamentos;
    private List<Recibo> recibos;

    public Socio(List<String> mensagens, List<Pagamento> pagamentos, List<Recibo> recibos) {
        this.mensagens = mensagens;
        this.pagamentos = pagamentos;
        this.recibos = recibos;
    }

    public Socio() {

    }


    public List<String> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<String> mensagens) {
        this.mensagens = mensagens;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public List<Recibo> getRecibos() {
        return recibos;
    }

    public void setRecibos(List<Recibo> recibos) {
        this.recibos = recibos;
    }
}
