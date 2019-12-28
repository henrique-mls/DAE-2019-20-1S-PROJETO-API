package ejbs;

import entities.Atleta;
import entities.Socio;
import entities.User;
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

@Stateless(name = "AtletaEJB")
public class AtletaBean {
    @PersistenceContext
    EntityManager em;
    public AtletaBean() {
    }

    public void create(String username, String name, String password, String email)
            throws MyEntityExistsException, MyConstraintViolationException
    {
        try {
            Atleta atleta = (Atleta) em.find(Atleta.class, username);
            if(atleta != null){
                throw  new MyEntityExistsException("Atleta with username " + username + " already exists!");
            }
            atleta = new Atleta(username, name, password, email);
            em.persist(atleta);
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
            Atleta atleta = (Atleta) em.find(Atleta.class, username);
            if(atleta == null){
                throw new MyEntityNotFoundException("Atleta with username " + username + " does not exist!");
            }
            atleta.setName(name);
            atleta.setPassword(User.hashPassword(password));
            atleta.setEmail(email);

        }catch(MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_UPDATING_ATLETA -> " + e.getMessage());
        }
    }

    public List<Atleta> all() {
        try {
            return (List<Atleta>) em.createNamedQuery("getAllAtletas").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ATLETAS", e);
        }
    }

    public Atleta findAtleta(String username) {
        try{
            return em.find(Atleta.class, username);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_ATLETA -> " + e.getMessage());
        }
    }

    public void remove(String username) throws MyEntityNotFoundException {
        try{
            Atleta atleta = em.find(Atleta.class, username);
            if(atleta == null){
                throw  new MyEntityNotFoundException("Atleta with username " + username + " does not exist!");
            }
            em.remove(atleta);
        }catch (MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_REMOVING_ATLETA -> " + e.getMessage());
        }
    }
}
