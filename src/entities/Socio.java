package entities;

import javax.jms.Message;
import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@NamedQueries({
        @NamedQuery(
                name = "getAllSocios",
                query = "SELECT s FROM Socio s ORDER BY s.name" // JPQL
        )
})

@Entity
public class Socio extends User {
    @OneToMany
    private List<Mensagem> mensagens;
    @OneToMany(mappedBy = "socio", cascade = CascadeType.REMOVE)
    private List<Pagamento> pagamentos;

    public Socio(String username, String name, String password, String email) {
        super(username, name, password, email);
        this.mensagens = new LinkedList<>();
        this.pagamentos = new LinkedList<>();
    }

    public Socio() {
        super();
        this.mensagens = new LinkedList<>();
        this.pagamentos = new LinkedList<>();

    }
    public void addPagamento(Pagamento pagamento){
        if(pagamento == null || pagamentos.contains(pagamento)){
            return;
        }
        pagamentos.add(pagamento);
    }

    public void removePagamento(Pagamento pagamento){
        if(pagamento == null || !pagamentos.contains(pagamento)){
            return;
        }
        pagamentos.remove(pagamento);
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public void addMensagem(Mensagem mensagem){
        if(mensagem == null){
            return;
        }
        mensagens.add(mensagem);
    }

    public void removeMensagem(Mensagem mensagem){
        if(mensagem == null || !mensagens.contains(mensagem)){
            return;
        }
        mensagens.remove(mensagem);
    }

}
