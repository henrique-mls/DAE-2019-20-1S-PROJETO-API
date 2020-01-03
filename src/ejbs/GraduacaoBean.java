package ejbs;

import entities.*;
import entities.Graduacao;
import entities.Graduacao;
import exceptions.*;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.time.DayOfWeek;
import java.util.List;

@Stateless(name = "GraduacaoEJB")
public class GraduacaoBean {
    @PersistenceContext
    EntityManager em;

    public GraduacaoBean() {
    }

    public void create(int id, String nome, String descricao)
            throws MyEntityExistsException, MyConstraintViolationException
    {
        try {
            Graduacao graduacao =  em.find(Graduacao.class,id);
            if(graduacao != null){
                throw  new MyEntityExistsException("Graduacao with id " + id + " already exists!");
            }
            graduacao = new Graduacao(id,nome,descricao);
            em.persist(graduacao);
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public Graduacao findGraduacao(int id) {
        try{
            return em.find(Graduacao.class,id);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_GRADUACAO-> " + e.getMessage());
        }
    }

    public List<Graduacao> all() {
        try {
            return (List<Graduacao>) em.createNamedQuery("getAllGraduacoes").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ATLETAS", e);
        }
    }

    public void remove(int id) throws MyEntityNotFoundException {
        try{
            Graduacao graduacao = em.find(Graduacao.class,id);
            if(graduacao == null){
                throw  new MyEntityNotFoundException("Graduacao with id " + id + " does not exist!");
            }
            em.remove(graduacao);
        }catch (MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_REMOVING_HORARIO -> " + e.getMessage());
        }
    }

    public void enrollAtletaInGraduacao(String username, int graduacaoId) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Atleta atleta = em.find(Atleta.class,username);
            Graduacao graduacao = em.find(Graduacao.class, graduacaoId);
            if(username == null){
                throw  new MyEntityNotFoundException("Atleta com o username " + username + " não existe!");
            }
            if(graduacao == null) {
                throw new MyEntityNotFoundException("Graduacao com o ID " + graduacaoId + " não existe!");
            }
            if(atleta.getGraduacoes().contains(graduacao)){
                throw new MyIllegalArgumentException("A Atleta com o ID " + username + " já tem o graduacao com o ID " + graduacaoId + "!");
            }
            atleta.addGraduacao(graduacao);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e){
            throw e;
        } catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_ENTITIES -> " + e.getMessage());
        }
    }

    public void unrollAtletaInGraduacao(String username, int graduacaoId) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Atleta atleta = em.find(Atleta.class,username);
            Graduacao graduacao = em.find(Graduacao.class, graduacaoId);
            if(username == null){
                throw  new MyEntityNotFoundException("Atleta com o username " + username + " não existe!");
            }
            if(graduacao == null) {
                throw new MyEntityNotFoundException("Graduacao com o ID " + graduacaoId + " não existe!");
            }
            if(!atleta.getGraduacoes().contains(graduacao)){
                throw new MyIllegalArgumentException("A Atleta com o ID " + username + " não tem a graduacao com o ID " + graduacaoId + "!");
            }
            atleta.removeGraduacao(graduacao);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e){
            throw e;
        } catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_ENTITIES -> " + e.getMessage());
        }
    }
}
