package ejbs;

import entities.Horario;
import entities.Modalidade;
import exceptions.*;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless(name = "ModalidadeEJB")
public class ModalidadeBean {
    @PersistenceContext
    EntityManager em;

    public ModalidadeBean() {
    }

    public void create(int id, String nome)
            throws MyEntityExistsException, MyConstraintViolationException
    {

        try {
            Modalidade modalidade = (Modalidade) em.find(Modalidade.class,id);
            if(modalidade != null){
                throw  new MyEntityExistsException("Modalidade with id " + id + " already exists!");
            }
            modalidade = new Modalidade(id,nome);
            em.persist(modalidade);
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch(MyEntityExistsException e){
            throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(int id, String nome) throws MyEntityNotFoundException {
        try{
            Modalidade modalidade = (Modalidade) em.find(Modalidade.class, id);
            if(modalidade == null){
                throw new MyEntityNotFoundException("Modalidade  with id " + id + " does not exist!");
            }
            modalidade.setNome(nome);
        }catch(MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_UPDATING_MODALIDADE -> " + e.getMessage());
        }
    }

    public List<Modalidade> all() {
        try {
            return (List<Modalidade>) em.createNamedQuery("getAllModalidades").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_MODALIDADES", e);
        }
    }

    public Modalidade findModalidade(int id) {
        try{
            return em.find(Modalidade.class, id);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_MODALIDADE -> " + e.getMessage());
        }
    }

    public void remove(int  id) throws MyEntityNotFoundException{
        try{
            Modalidade modalidade = em.find(Modalidade.class,id);
            if(modalidade == null){
                throw  new MyEntityNotFoundException("Modalide  with id " + id + " does not exist!");
            }
            em.remove(modalidade);
        }catch (MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_REMOVING_MODALIDADE -> " + e.getMessage());
        }
    }

    public void enrollModalidadeInHorario(int modalidadeID, int horarioID) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Modalidade modalidade = em.find(Modalidade.class,modalidadeID);
            Horario horario = em.find(Horario.class, horarioID);
//            if(modalidade == null){
//                throw  new MyEntityNotFoundException("Student with username " + username + " does not exist!");
//            }
//            if(horario == null){
//                throw new MyEntityNotFoundException("Subject with code " + subjectCode + " does not exist!");
//            }
//            if(horario.getStudents().contains(modalidade)){
//                throw new MyIllegalArgumentException("Student with username " + username +  "  is already enrolled in the subject " + horario.getName() + "!");
//            }
//            if(!modalidade.getHorario().getSubjects().contains(horario)){
//                throw new MyIllegalArgumentException("Course with code " + modalidade.getCourse().getCode() + " does not contain the subject " + horario.getName() + "!");
//            }
//            horario.addStudent(modalidade);
            modalidade.addHorario(horario);
        } catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_ENTITIES -> " + e.getMessage());
        }
    }
}
