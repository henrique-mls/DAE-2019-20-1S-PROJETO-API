package ejbs;

import entities.*;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Stateless(name = "PagamentoEJB")
public class PagamentoBean {
    @PersistenceContext
    EntityManager em;

    public PagamentoBean() {
    }

    public void create(int id, String username, int produtoID, Date dataLancamento, int quantidade, float precoFinal, Estado estado/*, Recibo recibo*/)
            throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        try{
            System.out.println("*****************************************************");
            System.out.println(id + username + produtoID+ dataLancamento+ quantidade + precoFinal + estado);
            System.out.println("*****************************************************");
            Pagamento pagamento = em.find(Pagamento.class,id);
            if(pagamento != null){
                throw new MyEntityExistsException("Pagamento with id " + id + " already exists!");
            }
            User user = em.find(User.class,username);
            if(user == null){
                throw new MyEntityNotFoundException("User with username " + username + " doesnt exist!");
            }
            Produto produto = em.find(Produto.class,produtoID);
            if(produto == null){
                throw new MyEntityNotFoundException("Produto with id " + id + " doesnt exist!");
            }
            pagamento = new Pagamento(id,user,produto,dataLancamento,quantidade,precoFinal,estado/*,recibo*/);
            em.persist(pagamento);

        }catch (Exception e){
            throw new EJBException("ERROR_CREATING_PAGAMENTO -> " + e.getMessage());
        }
    }

    public List<Pagamento> all() {
        try {
            return (List<Pagamento>) em.createNamedQuery("getAllPagamentos").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_PAGAMENTO", e);
        }
    }

    public Pagamento findPagamento(int id) {
        try{
            return em.find(Pagamento.class, id);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_PAGAMENTO -> " + e.getMessage());
        }
    }
}
