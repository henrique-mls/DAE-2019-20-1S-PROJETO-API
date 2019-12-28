package ejbs;

import entities.Horario;
import entities.Modalidade;
import entities.Treinador;
import entities.User;
import exceptions.*;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless(name = "TreinadorEJB")
public class TreinadorBean extends UserBean {
    @PersistenceContext
    EntityManager em;
    public TreinadorBean() {
    }
    public void create(String username, String name, String password, String email)
            throws MyEntityExistsException, MyConstraintViolationException
    {
        try {
            Treinador treinador = (Treinador) em.find(Treinador.class, username);
            if(treinador != null){
                throw  new MyEntityExistsException("Treinador with username " + username + " already exists!");
            }
            treinador = new Treinador(username ,name, password, email);
            em.persist(treinador);
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
            Treinador treinador = (Treinador) em.find(Treinador.class, username);
            if(treinador == null){
                throw new MyEntityNotFoundException("Treinador with username " + username + " does not exist!");
            }
            treinador.setName(name);
            treinador.setPassword(User.hashPassword(password));
            treinador.setEmail(email);

        }catch(MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_UPDATING_TREINADOR -> " + e.getMessage());
        }
    }

    public List<Treinador> all() {
        try {
            return (List<Treinador>) em.createNamedQuery("getAllTreinadores").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_TREINADORES", e);
        }
    }

    public Treinador findTreinador(String username) {
        try{
            return em.find(Treinador.class, username);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_TREINADOR -> " + e.getMessage());
        }
    }

    public void remove(String username) throws MyEntityNotFoundException {
        try{
            Treinador treinador = em.find(Treinador.class, username);
            if(treinador == null){
                throw  new MyEntityNotFoundException("Treinador with username " + username + " does not exist!");
            }
            em.remove(treinador);
        }catch (MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_REMOVING_TREINADOR -> " + e.getMessage());
        }
    }

    public void enrollTreinadorInHorario(String treinadorUsername, int horarioID) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Treinador treinador = em.find(Treinador.class,treinadorUsername);
            Horario horario = em.find(Horario.class, horarioID);
            if(treinadorUsername == null){
                throw  new MyEntityNotFoundException("Treinador com o username " + treinadorUsername + " não existe!");
            }
            if(horario == null) {
                throw new MyEntityNotFoundException("Horario com o ID " + horarioID + " não existe!");
            }
            if(treinador.getHorarios().contains(horario)){
                throw new MyIllegalArgumentException("A Treinador com o ID " + treinadorUsername + " já tem o horario com o ID " + horarioID + "!");
            }
            treinador.addHorario(horario);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e){
            throw e;
        } catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_ENTITIES -> " + e.getMessage());
        }
    }

    public void unrollTreinadorInHorario(String treinadorUsername, int horarioID) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Treinador treinador = em.find(Treinador.class,treinadorUsername);
            Horario horario = em.find(Horario.class, horarioID);
            if(treinadorUsername == null){
                throw  new MyEntityNotFoundException("Treinador com o username " + treinadorUsername + " não existe!");
            }
            if(horario == null) {
                throw new MyEntityNotFoundException("Horario com o ID " + horarioID + " não existe!");
            }
            if(!treinador.getHorarios().contains(horario)){
                throw new MyIllegalArgumentException("A Treinador com o ID " + treinadorUsername + " não tem o horario com o ID " + horarioID + "!");
            }
            treinador.removeHorario(horario);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e){
            throw e;
        } catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_ENTITIES -> " + e.getMessage());
        }
    }
}
