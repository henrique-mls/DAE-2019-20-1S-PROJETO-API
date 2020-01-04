package ejbs;

import entities.Administrador;
import entities.Atleta;
import entities.Mensagem;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.Utils;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

@Stateless(name = "MensagemEJB")
public class MensagemBean {
    public MensagemBean() {
    }

    @PersistenceContext
    EntityManager em;

    public void create(String username, String subject, String message)
            throws MyConstraintViolationException
    {

        try {
            Atleta atleta = (Atleta) em.find(Atleta.class, username);
            Mensagem mensagem = new Mensagem(subject, message);
            em.persist(mensagem);
            atleta.addMensagem(mensagem);
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

}
