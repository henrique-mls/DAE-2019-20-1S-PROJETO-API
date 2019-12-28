package ejbs;

import entities.Horario;
import entities.Modalidade;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.Utils;
import org.hibernate.validator.internal.engine.resolver.JPATraversableResolver;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.time.DayOfWeek;
import java.util.List;

@Stateless(name = "HorarioSessionEJB")
public class HorarioBean {
    @PersistenceContext
    EntityManager em;

    public HorarioBean() {
    }

    public void create(int id, DayOfWeek dia, int horaInicio, int duracao)
            throws MyEntityExistsException, MyConstraintViolationException
    {

        try {
            Horario horario = (Horario) em.find(Horario.class,id);
            if(horario != null){
                throw new MyEntityExistsException("Horario with id " + id + " already exists!");
            }
            horario = new Horario(id, dia,horaInicio,duracao);
            em.persist(horario);
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch(MyEntityExistsException e){
            throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
