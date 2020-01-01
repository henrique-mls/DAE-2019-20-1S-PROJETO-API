package ejbs;

import entities.*;
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
            if(password!=null){
                treinador.setPassword(User.hashPassword(password));
            }
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

    public void enrollTreinadorInEscalaoInModalidade(String username,int escalaoID,int modalidadeID) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Modalidade modalidade = em.find(Modalidade.class,modalidadeID);
            Treinador treinador = em.find(Treinador.class, username);
            Escalao escalao = em.find(Escalao.class,escalaoID);
            if(modalidade == null){
                throw  new MyEntityNotFoundException("Modalidade com o ID " + modalidadeID + " não existe!");
            }
            if(treinador == null) {
                throw new MyEntityNotFoundException("Treinador com o ID " + username + " não existe!");
            }
            if(escalao == null){
                throw  new MyEntityNotFoundException("Escalao com o ID " + escalaoID + " não existe!");
            }
            if(!modalidade.getEscaloes().contains(escalao)){
                throw new MyIllegalArgumentException("A Modalidade com o ID " + modalidadeID + " nao tem o escalao com o id " + escalaoID + "!");
            }
            if(escalao.getTreinadores().contains(treinador)){
                throw new MyIllegalArgumentException("A O Escalao com o ID " + escalaoID + "já tem o treinador com o username " + username + "!");
            }
            escalao.addTreinador(treinador);
            treinador.addModalidade(modalidade);
            treinador.addEscalao(escalao);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e){
            throw e;
        } catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_ENTITIES -> " + e.getMessage());
        }
    }

    public void unrollTreinadorInEscalaoInModalidade(String username ,int escalaoID,int modalidadeID) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Modalidade modalidade = em.find(Modalidade.class,modalidadeID);
            Treinador treinador = em.find(Treinador.class,username);
            Escalao escalao = em.find(Escalao.class,escalaoID);
            if(modalidade == null){
                throw  new MyEntityNotFoundException("Modalidade com o ID " + modalidadeID + " não existe!");
            }
            if(treinador == null) {
                throw new MyEntityNotFoundException("Treinador com o usernamne " + username + " não existe!");
            }
            if(escalao == null) {
                throw new MyEntityNotFoundException("Escalao com o id " + username + " não existe!");
            }
            if(!modalidade.getEscaloes().contains(escalao)){
                throw new MyIllegalArgumentException("A Modalidade com o ID " + modalidadeID + " nao tem o escalao com o id " + escalaoID + "!");
            }
            if(!escalao.getTreinadores().contains(treinador)){
                throw new MyIllegalArgumentException("O Escalao com o ID " + modalidadeID + " não tem o treinador com o username " + username + "!");
            }
            escalao.removeTreinador(treinador);
            treinador.removeEscalao(escalao);
            boolean hasEscaloesModalidade = false;
            for (Escalao treinadorEsca : treinador.getEscaloes()) {
                for (Escalao modalidadeEsca : modalidade.getEscaloes()){
                    if(modalidadeEsca.getId() == treinadorEsca.getId()){
                        hasEscaloesModalidade = true;
                    }
                }
            }
            if(!hasEscaloesModalidade){
                treinador.removeModalidade(modalidade);
            }
            escalao.removeTreinador(treinador);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e){
            throw e;
        } catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_ENTITIES -> " + e.getMessage());
        }
    }
}
