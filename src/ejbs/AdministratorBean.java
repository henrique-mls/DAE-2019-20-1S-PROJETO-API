package ejbs;

import entities.Administrator;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.Utils;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless(name = "AdministratorEJB")
public class AdministratorBean {
    @PersistenceContext
    EntityManager em;

    public void create(String username, String name, String password, String email)
            throws MyEntityExistsException, MyConstraintViolationException
    {

        try {
            Administrator administrator = (Administrator) em.find(Administrator.class, username);
            if(administrator != null){
                throw  new MyEntityExistsException("Administrator with username " + username + " already exists!");
            }
            administrator = new Administrator(username, name, password, email);
            em.persist(administrator);
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
            Administrator administrator = (Administrator) em.find(Administrator.class, username);
            if(administrator == null){
                throw new MyEntityNotFoundException("Administrator with username " + username + " does not exist!");
            }
            //em.lock(administrator, LockModeType.OPTIMISTIC);
            administrator.setPassword(password);
            administrator.setName(name);
            administrator.setEmail(email);

        }catch(MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_UPDATING_ADMINISTRATOR -> " + e.getMessage());
        }
    }

    public List<Administrator> all() {
        try {
            return (List<Administrator>) em.createNamedQuery("getAllAdministrators").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ADMINISTRATORS", e);
        }
    }

    public Administrator findAdministrator(String username) {
        try{
            return em.find(Administrator.class, username);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_ADMINISTRATOR -> " + e.getMessage());
        }
    }

    public void remove(String username) throws MyEntityNotFoundException{
        try{
            Administrator administrator = em.find(Administrator.class, username);
            if(administrator == null){
                throw  new MyEntityNotFoundException("Administrator with username " + username + " does not exist!");
            }
            em.remove(administrator);
        }catch (MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_REMOVING_ADMISTRATOR -> " + e.getMessage());
        }
    }
}
