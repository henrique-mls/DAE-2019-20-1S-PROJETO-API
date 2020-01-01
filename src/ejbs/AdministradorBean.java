package ejbs;

import entities.Administrador;
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

@Stateless(name = "AdministratorEJB")
public class AdministradorBean {
    @PersistenceContext
    EntityManager em;

    public void create(String username, String name, String password, String email)
            throws MyEntityExistsException, MyConstraintViolationException
    {

        try {
            Administrador administrador = (Administrador) em.find(Administrador.class, username);
            if(administrador != null){
                throw  new MyEntityExistsException("Administrator with username " + username + " already exists!");
            }
            administrador = new Administrador(username, name, password, email);
            em.persist(administrador);
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch(MyEntityExistsException e){
            throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(String username, String name, String password, String email) throws MyEntityNotFoundException{
        try{
            Administrador administrador = (Administrador) em.find(Administrador.class, username);
            if(administrador == null){
                throw new MyEntityNotFoundException("Administrator with username " + username + " does not exist!");
            }
            //em.lock(administrator, LockModeType.OPTIMISTIC);
            if(password!=null){
                administrador.setPassword(User.hashPassword(password));
            }
            administrador.setName(name);
            administrador.setEmail(email);

        }catch(MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_UPDATING_ADMINISTRATOR -> " + e.getMessage());
        }
    }

    public List<Administrador> all() {
        try {
            return (List<Administrador>) em.createNamedQuery("getAllAdministrators").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ADMINISTRATORS", e);
        }
    }

    public Administrador findAdministrator(String username) {
        try{
            return em.find(Administrador.class, username);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_ADMINISTRATOR -> " + e.getMessage());
        }
    }

    public void remove(String username) throws MyEntityNotFoundException{
        try{
            Administrador administrador = em.find(Administrador.class, username);
            if(administrador == null){
                throw  new MyEntityNotFoundException("Administrator with username " + username + " does not exist!");
            }
            em.remove(administrador);
        }catch (MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_REMOVING_ADMISTRATOR -> " + e.getMessage());
        }
    }
}
