package ejbs;

import entities.*;
import entities.Presencas;
import exceptions.*;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless(name = "PresencasEJB")
public class PresencasBean {
    @PersistenceContext
    EntityManager em;

    public PresencasBean() {
    }

    public List<Presencas> all() {
        try {
            return (List<Presencas>) em.createNamedQuery("getAllPresencas").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ESCALOES", e);
        }
    }
    public Presencas findPresencas(int id) {
        try{
            return em.find(Presencas.class, id);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_PRESENCAS -> " + e.getMessage());
        }
    }
    public void create(int id, String date,int escalaoID)
            throws MyEntityExistsException, MyConstraintViolationException
    {
        try {
            Presencas presencas = (Presencas) em.find(Presencas.class,id);
            if(presencas != null){
                throw  new MyEntityExistsException("Presencas with id " + id + " already exists!");
            }
            Escalao escalao = em.find(Escalao.class,escalaoID);
            if(escalao == null){
                throw new MyEntityExistsException("Escalao with id "+ escalaoID + " doesnt exist!");
            }
            Date newDate = new SimpleDateFormat("yyyy-mm-dd").parse(date);
            presencas = new Presencas(id,newDate,escalao);
            Map<String,Boolean> mapa = new HashMap<>();
            for (Atleta atleta : escalao.getAtletas()) {
                mapa.put(atleta.getUsername(),false);
            }
            presencas.setMapaPresencas(mapa);
            em.persist(presencas);

            escalao.addPresencas(presencas);

        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch(MyEntityExistsException e){
            throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(int id, String date, int escalaoID) throws MyEntityNotFoundException {
        try{
            Presencas presencas = (Presencas) em.find(Presencas.class, id);
            if(presencas == null){
                throw new MyEntityNotFoundException("Presencas  with id " + id + " does not exist!");
            }

            Escalao escalao = em.find(Escalao.class,escalaoID);
            if(escalao == null){
                throw new MyEntityNotFoundException("Escalao  with id " + escalaoID + " does not exist!");
            }
        }catch(MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_UPDATING_ESCALOES -> " + e.getMessage());
        }
    }

    public void addOrUpdatePresencaInPresencas(int id, String username,Boolean presente) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Presencas presencas = (Presencas) em.find(Presencas.class, id);
            if(presencas == null){
                throw new MyEntityNotFoundException("Presencas  with id " + id + " does not exist!");
            }
            Atleta atleta = em.find(Atleta.class,username);
            if(atleta == null){
                throw new MyEntityNotFoundException("Atleta with username " + username + " does not exist!");
            }
            if(presencas.getMapaPresencas().containsKey(username)){
                presencas.getMapaPresencas().replace(username,presente);
                return;
            }
            presencas.addPresenca(username,presente);
        }catch (MyEntityNotFoundException e){
            throw e;
        } catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_ENTITIES -> " + e.getMessage());
        }
    }

    public void removePresencaInPresencas(int id, String username) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Presencas presencas = (Presencas) em.find(Presencas.class, id);
            if(presencas == null){
                throw new MyEntityNotFoundException("Presencas  with id " + id + " does not exist!");
            }
            Atleta atleta = em.find(Atleta.class,username);
            if(atleta == null){
                throw new MyEntityNotFoundException("Atleta with username " + username + " does not exist!");
            }
            if(!presencas.getMapaPresencas().containsKey(username)){
                throw new MyEntityNotFoundException("Presenca with id " +id+" doesnt have atleta " + username);
            }

            presencas.removePresenca(username);
        }catch (MyEntityNotFoundException e){
            throw e;
        } catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_ENTITIES -> " + e.getMessage());
        }
    }
}
