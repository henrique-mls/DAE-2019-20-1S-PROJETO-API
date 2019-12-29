package ejbs;

import entities.*;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.Utils;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;

@Stateless(name = "PagamentoEJB")
public class PagamentoBean {
    @PersistenceContext
    EntityManager em;

    public PagamentoBean() {
    }

    public void create(int id, String username,  int produtoID,Date dataLancamento, int quantidade, float precoFinal, Estado estado/*, Recibo recibo*/)
            throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        try{
            Pagamento pagamento = em.find(Pagamento.class,id );
            if(pagamento != null){
                throw new MyEntityExistsException("Pagamento with id " + id + " already exists!");
            }
            Socio socio = em.find(Socio.class,username);
            if(socio == null){
                throw new MyEntityNotFoundException("User with username " + username + " doesnt exist!");
            }
            Produto produto = em.find(Produto.class,produtoID);
            if(produto == null){
                throw new MyEntityNotFoundException("Produto with id " + produtoID + " doesnt exist!");
            }
            pagamento = new Pagamento(id,socio,produto,dataLancamento,quantidade,precoFinal,estado);
            em.persist(pagamento);

        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch(MyEntityExistsException | MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
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
            Pagamento pagamento = em.find(Pagamento.class, id);
            if(pagamento == null){
                throw new MyEntityNotFoundException("Pagamento  with id " + id + " does not exist!");
            }
            Socio socio = em.find(Socio.class,username);
            if(socio == null){
                throw new MyEntityNotFoundException("User with username " + username + " doesnt exist!");
            }
            Produto produto = em.find(Produto.class,produtoID);
            if(produto == null){
                throw new MyEntityNotFoundException("Produto with id " + id + " doesnt exist!");
            }

            pagamento.setDataLancamento(dataLancamento);
            pagamento.setQuantidade(quantidade);
            pagamento.setPrecoFinal(precoFinal);
            pagamento.setEstado(estado);
            if(username != pagamento.getSocio().getUsername()){
                pagamento.getSocio().removePagamento(pagamento);
                pagamento.setSocio(socio);
                socio.addPagamento(pagamento);
            }
            if(produtoID != pagamento.getProduto().getId()){
                pagamento.getProduto().removePagamento(pagamento);
                pagamento.setProduto(produto);
                produto.addPagamento(pagamento);
            }
            //pagamento.setSocio(socio);
            //pagamento.setProduto(produto);
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
