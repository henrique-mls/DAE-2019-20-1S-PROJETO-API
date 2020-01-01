package ejbs;

import entities.Escalao;
import entities.Horario;
import entities.Escalao;
import entities.Modalidade;
import exceptions.*;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless(name = "EscalaoEJB")
public class EscalaoBean {
    @PersistenceContext
    EntityManager em;

    public EscalaoBean() {
    }

    public void create(int id, String nome,int modalidadeID)
            throws MyEntityExistsException, MyConstraintViolationException
    {

        try {
            Escalao escalao = (Escalao) em.find(Escalao.class,id);
            if(escalao != null){
                throw  new MyEntityExistsException("Escalao with id " + id + " already exists!");
            }
            Modalidade modalidade = (Modalidade) em.find(Modalidade.class,modalidadeID);
            if(modalidade == null){
                throw  new MyEntityExistsException("modalidade with id " + id + " doesnt exists!");
            }
            escalao = new Escalao(id,nome,modalidade);
            em.persist(escalao);
            if(!modalidade.getEscaloes().contains(escalao)){
                modalidade.addEscalao(escalao);
            }
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch(MyEntityExistsException e){
            throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(int id, String nome,int modalidadeID) throws MyEntityNotFoundException {
        try{
            Escalao escalao = (Escalao) em.find(Escalao.class, id);
            if(escalao == null){
                throw new MyEntityNotFoundException("Escalao  with id " + id + " does not exist!");
            }
            Modalidade modalidade = (Modalidade) em.find(Modalidade.class,modalidadeID);
            if(modalidade == null){
                throw  new MyEntityExistsException("modalidade with id " + id + " doesnt exists!");
            }
            if(escalao.getModalidade() != null){
                if(modalidadeID != escalao.getModalidade().getId()){
                    escalao.getModalidade().removeEscalao(escalao);
                    escalao.setModalidade(modalidade);
                    modalidade.addEscalao(escalao);
                }
            }else{
                escalao.setModalidade(modalidade);
                modalidade.addEscalao(escalao);
            }
            escalao.setNome(nome);
        }catch(MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_UPDATING_ESCALOES -> " + e.getMessage());
        }
    }

    public List<Escalao> all() {
        try {
            return (List<Escalao>) em.createNamedQuery("getAllEscaloes").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ESCALOES", e);
        }
    }

    public Escalao findEscalao(int id) {
        try{
            return em.find(Escalao.class, id);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_MODALIDADE -> " + e.getMessage());
        }
    }

    public void remove(int  id) throws MyEntityNotFoundException{
        try{
            Escalao escalao = em.find(Escalao.class,id);
            if(escalao == null){
                throw  new MyEntityNotFoundException("Escalao  with id " + id + " does not exist!");
            }
            em.remove(escalao);
        }catch (MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_REMOVING_ESCALAO -> " + e.getMessage());
        }
    }

    public void enrollHorarioInEscalao(int escalaoID, int horarioID) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Escalao escalao = em.find(Escalao.class,escalaoID);
            Horario horario = em.find(Horario.class, horarioID);
            if(escalao == null){
                throw  new MyEntityNotFoundException("Escalao com o ID " + escalaoID + " não existe!");
            }
            if(horario == null) {
                throw new MyEntityNotFoundException("Horario com o ID " + horarioID + " não existe!");
            }
            if(escalao.getHorarios().contains(horario)){
                throw new MyIllegalArgumentException("A Escalao com o ID " + escalaoID + " já tem o horario com o ID " + horarioID + "!");
            }
            escalao.addHorario(horario);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e){
            throw e;
        } catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_ENTITIES -> " + e.getMessage());
        }
    }

    public void unrollHorarioInEscalao(int escalaoID, int horarioID) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Escalao escalao = em.find(Escalao.class,escalaoID);
            Horario horario = em.find(Horario.class, horarioID);
            if(escalao == null){
                throw  new MyEntityNotFoundException("Escalao com o ID " + escalaoID + " não existe!");
            }
            if(horario == null) {
                throw new MyEntityNotFoundException("Horario com o ID " + horarioID + " não existe!");
            }
            if(!escalao.getHorarios().contains(horario)){
                throw new MyIllegalArgumentException("A Escalao com o ID " + escalaoID + " não tem o horario com o ID " + horarioID + "!");
            }
            escalao.removeHorario(horario);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e){
            throw e;
        } catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_ENTITIES -> " + e.getMessage());
        }
    }
}
