package ejbs;

import entities.Produto;
import entities.TipoProduto;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.Utils;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless(name = "ProdutoEJB")
public class ProdutoBean {
    @PersistenceContext
    EntityManager em;


    public ProdutoBean() {
    }

    public void create(int id, TipoProduto tipo, String descricao, float valorBase)
            throws MyEntityExistsException, MyConstraintViolationException
    {

        try {
            Produto produto = (Produto) em.find(Produto.class,id);
            if(produto != null){
                throw  new MyEntityExistsException("Produto with id " + id + " already exists!");
            }
            produto = new Produto(id,tipo,descricao,valorBase);
            em.persist(produto);
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch(MyEntityExistsException e){
            throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(int id, TipoProduto tipo, String descricao, float valorBase) throws MyEntityNotFoundException {
        try{
            Produto produto = (Produto) em.find(Produto.class, id);
            if(produto == null){
                throw new MyEntityNotFoundException("Produto  with id " + id + " does not exist!");
            }
            produto.setTipo(tipo);
            produto.setDescricao(descricao);
            produto.setValorBase(valorBase);
        }catch(MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_UPDATING_PRODUTO -> " + e.getMessage());
        }
    }

    public List<Produto> all() {
        try {
            return (List<Produto>) em.createNamedQuery("getAllProdutos").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_PRODUTOS", e);
        }
    }

    public Produto findProduto(int id) {
        try{
            return em.find(Produto.class, id);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_PRODUTOS -> " + e.getMessage());
        }
    }

    public void remove(int  id) throws MyEntityNotFoundException{
        try{
            Produto produto = em.find(Produto.class,id);
            if(produto == null){
                throw  new MyEntityNotFoundException("Produto  with id " + id + " does not exist!");
            }
            em.remove(produto);
        }catch (MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_REMOVING_PRODUTO -> " + e.getMessage());
        }
    }
}
