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
                throw new MyEntityNotFoundException("Produto with id " + produtoID + " doesnt exist!");
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

    public void update(int id, String username, int produtoID, Date dataLancamento, int quantidade, float precoFinal, Estado estado)
            throws MyEntityNotFoundException {
        try{
            Pagamento pagamento = (Pagamento) em.find(Pagamento.class, id);
            if(pagamento == null){
                throw new MyEntityNotFoundException("Pagamento  with id " + id + " does not exist!");
            }
            User user = em.find(User.class,username);
            if(user == null){
                throw new MyEntityNotFoundException("User with username " + username + " doesnt exist!");
            }
            Produto produto = em.find(Produto.class,produtoID);
            if(produto == null){
                throw new MyEntityNotFoundException("Produto with id " + id + " doesnt exist!");
            }

            pagamento.setUtilizador(user);
            pagamento.setProduto(produto);
            pagamento.setDataLancamento(dataLancamento);
            pagamento.setQuantidade(quantidade);
            pagamento.setPrecoFinal(precoFinal);
            pagamento.setEstado(estado);
        }catch(MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_UPDATING_PAGAMENTO -> " + e.getMessage());
        }
    }

    public void remove(int  id) throws MyEntityNotFoundException{
        try{
            Pagamento pagamento = em.find(Pagamento.class,id);
            if(pagamento == null){
                throw  new MyEntityNotFoundException("Pagamento  with id " + id + " does not exist!");
            }
            em.remove(pagamento);
        }catch (MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_REMOVING_PAGAMENTO -> " + e.getMessage());
        }
    }
}
