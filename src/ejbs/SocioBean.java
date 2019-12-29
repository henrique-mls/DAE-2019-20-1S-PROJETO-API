package ejbs;

import entities.*;
import exceptions.*;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless(name = "SocioEJB")
public class SocioBean {
    @PersistenceContext
    EntityManager em;
    public SocioBean() {
    }

    public void create(String username, String name, String password, String email)
            throws MyEntityExistsException, MyConstraintViolationException
    {
        try {
            Socio socio = (Socio) em.find(Socio.class, username);
            if(socio != null){
                throw  new MyEntityExistsException("Socio with username " + username + " already exists!");
            }
            socio = new Socio(username, name, password, email);
            em.persist(socio);
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch(MyEntityExistsException e){
            throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(String username, String name, String password, String email) throws MyEntityNotFoundException {
        try{
            Socio socio = (Socio) em.find(Socio.class, username);
            if(socio == null){
                throw new MyEntityNotFoundException("Socio with username " + username + " does not exist!");
            }
            socio.setName(name);
            socio.setPassword(User.hashPassword(password));
            socio.setEmail(email);

        }catch(MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_UPDATING_SOCIO -> " + e.getMessage());
        }
    }

    public List<Socio> all() {
        try {
            return (List<Socio>) em.createNamedQuery("getAllSocios").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_SOCIOS", e);
        }
    }

    public Socio findSocio(String username) {
        try{
            return em.find(Socio.class, username);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_SOCIO -> " + e.getMessage());
        }
    }

    public void remove(String username) throws MyEntityNotFoundException {
        try{
            Socio socio = em.find(Socio.class, username);
            if(socio == null){
                throw  new MyEntityNotFoundException("Socio with username " + username + " does not exist!");
            }
            em.remove(socio);
        }catch (MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_REMOVING_SOCIO -> " + e.getMessage());
        }
    }

    public void enrollSocioInPagamento(String username, int pagamentoID) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Socio socio = em.find(Socio.class,username);
            Pagamento pagamento = em.find(Pagamento.class, pagamentoID);
            if(socio == null){
                throw  new MyEntityNotFoundException("Socio com o username " + username + " não existe!");
            }
            if(pagamento == null) {
                throw new MyEntityNotFoundException("Pagamento com o ID " + pagamentoID + " não existe!");
            }
            if(socio.getPagamentos().contains(pagamento)){
                throw new MyIllegalArgumentException("Socio com o username " + username + " já tem o pagamento com o ID " + pagamentoID + "!");
            }
            socio.addPagamento(pagamento);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e){
            throw e;
        } catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_ENTITIES -> " + e.getMessage());
        }
    }

    public void unrollSocioInPagamento(String username, int pagamentoID) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Socio socio = em.find(Socio.class,username);
            Pagamento pagamento = em.find(Pagamento.class, pagamentoID);
            if(socio == null){
                throw  new MyEntityNotFoundException("Socio com o username " + username + " não existe!");
            }
            if(pagamento == null) {
                throw new MyEntityNotFoundException("Pagamento com o ID " + pagamentoID + " não existe!");
            }
            if(!socio.getPagamentos().contains(pagamento)){
                throw new MyIllegalArgumentException("O Socio com o ID " + username + " não tem o pagamento com o ID " + pagamentoID + "!");
            }
            socio.removePagamento(pagamento);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e){
            throw e;
        } catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_ENTITIES -> " + e.getMessage());
        }
    }

}
