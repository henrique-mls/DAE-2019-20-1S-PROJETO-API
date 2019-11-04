package ejbs;

import entities.Administrator;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "AdministratorEJB")
public class AdministratorBean {
    @PersistenceContext
    EntityManager em;

    public void create(String username, String name, String password, String email) {

        try {
            Administrator administrator = new Administrator(username, name, password, email);
            em.persist(administrator);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<Administrator> all() {
        try {
            return (List<Administrator>) em.createNamedQuery("getAllAdministrators").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_STUDENTS", e);
        }
    }

    public Administrator findAdministrator(String username) {
        try{
            return em.find(Administrator.class, username);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_STUDENT -> " + e.getMessage());
        }
    }
}
