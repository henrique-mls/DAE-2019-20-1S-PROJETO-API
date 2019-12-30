package entities;

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
    private List<String> mensagens;
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

}
