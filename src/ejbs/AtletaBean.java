package ejbs;

import entities.*;
import exceptions.*;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless(name = "AtletaEJB")
public class AtletaBean {
    @PersistenceContext
    EntityManager em;
    public AtletaBean() {
    }

    public void create(String username, String name, String password, String email)
            throws MyEntityExistsException, MyConstraintViolationException
    {
        try {
            Atleta atleta = (Atleta) em.find(Atleta.class, username);
            if(atleta != null){
                throw  new MyEntityExistsException("Atleta with username " + username + " already exists!");
            }
            atleta = new Atleta(username, name, password, email);
            em.persist(atleta);
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
            Atleta atleta = (Atleta) em.find(Atleta.class, username);
            if(atleta == null){
                throw new MyEntityNotFoundException("Atleta with username " + username + " does not exist!");
            }
            atleta.setName(name);
            if(password!=null){
                atleta.setPassword(User.hashPassword(password));
            }
            atleta.setEmail(email);

        }catch(MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_UPDATING_ATLETA -> " + e.getMessage());
        }
    }

    public List<Atleta> all() {
        try {
            return (List<Atleta>) em.createNamedQuery("getAllAtletas").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_ATLETAS", e);
        }
    }

    public Atleta findAtleta(String username) {
        try{
            return em.find(Atleta.class, username);
        } catch (Exception e) {
            throw new EJBException("ERROR_FINDING_ATLETA -> " + e.getMessage());
        }
    }

    public void remove(String username) throws MyEntityNotFoundException {
        try{
            Atleta atleta = em.find(Atleta.class, username);
            if(atleta == null){
                throw  new MyEntityNotFoundException("Atleta with username " + username + " does not exist!");
            }
            em.remove(atleta);
        }catch (MyEntityNotFoundException e){
            throw e;
        }
        catch (Exception e){
            throw new EJBException("ERROR_REMOVING_ATLETA -> " + e.getMessage());
        }
    }
    public void enrollAtletaInHorario(String username, int horarioID) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Atleta atleta = em.find(Atleta.class,username);
            Horario horario = em.find(Horario.class, horarioID);
            if(username == null){
                throw  new MyEntityNotFoundException("Atleta com o username " + username + " não existe!");
            }
            if(horario == null) {
                throw new MyEntityNotFoundException("Horario com o ID " + horarioID + " não existe!");
            }
            if(atleta.getHorarios().contains(horario)){
                throw new MyIllegalArgumentException("A Atleta com o ID " + username + " já tem o horario com o ID " + horarioID + "!");
            }
            atleta.addHorario(horario);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e){
            throw e;
        } catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_ENTITIES -> " + e.getMessage());
        }
    }

    public void unrollAtletaInHorario(String username, int horarioID) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Atleta atleta = em.find(Atleta.class,username);
            Horario horario = em.find(Horario.class, horarioID);
            if(username == null){
                throw  new MyEntityNotFoundException("Atleta com o username " + username + " não existe!");
            }
            if(horario == null) {
                throw new MyEntityNotFoundException("Horario com o ID " + horarioID + " não existe!");
            }
            if(!atleta.getHorarios().contains(horario)){
                throw new MyIllegalArgumentException("A Atleta com o ID " + username + " não tem o horario com o ID " + horarioID + "!");
            }
            atleta.removeHorario(horario);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e){
            throw e;
        } catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_ENTITIES -> " + e.getMessage());
        }
    }

    public void enrollAtletaInEscalaoInModalidade(String username,int escalaoID,int modalidadeID) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Modalidade modalidade = em.find(Modalidade.class,modalidadeID);
            Atleta atleta = em.find(Atleta.class, username);
            Escalao escalao = em.find(Escalao.class,escalaoID);
            if(modalidade == null){
                throw  new MyEntityNotFoundException("Modalidade com o ID " + modalidadeID + " não existe!");
            }
            if(atleta == null) {
                throw new MyEntityNotFoundException("Atleta com o ID " + username + " não existe!");
            }
            if(escalao == null){
                throw  new MyEntityNotFoundException("Escalao com o ID " + escalaoID + " não existe!");
            }
            if(!modalidade.getEscaloes().contains(escalao)){
                throw new MyIllegalArgumentException("A Modalidade com o ID " + modalidadeID + " nao tem o escalao com o id " + escalaoID + "!");
            }
            if(escalao.getAtletas().contains(atleta)){
                throw new MyIllegalArgumentException("A O Escalao com o ID " + escalaoID + "já tem o atleta com o username " + username + "!");
            }
            escalao.addAtleta(atleta);
            atleta.addModalidade(modalidade);
            atleta.addEscalao(escalao);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e){
            throw e;
        } catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_ENTITIES -> " + e.getMessage());
        }
    }

    public void unrollAtletaInEscalaoInModalidade(String username ,int escalaoID,int modalidadeID) throws MyEntityNotFoundException, MyIllegalArgumentException {
        try{
            Modalidade modalidade = em.find(Modalidade.class,modalidadeID);
            Atleta atleta = em.find(Atleta.class,username);
            Escalao escalao = em.find(Escalao.class,escalaoID);
            if(modalidade == null){
                throw  new MyEntityNotFoundException("Modalidade com o ID " + modalidadeID + " não existe!");
            }
            if(atleta == null) {
                throw new MyEntityNotFoundException("Atleta com o usernamne " + username + " não existe!");
            }
            if(escalao == null) {
                throw new MyEntityNotFoundException("Escalao com o id " + username + " não existe!");
            }
            if(!modalidade.getEscaloes().contains(escalao)){
                throw new MyIllegalArgumentException("A Modalidade com o ID " + modalidadeID + " nao tem o escalao com o id " + escalaoID + "!");
            }
            if(!escalao.getAtletas().contains(atleta)){
                throw new MyIllegalArgumentException("O Escalao com o ID " + modalidadeID + " não tem o atleta com o username " + username + "!");
            }
            escalao.removeAtleta(atleta);
            atleta.removeEscalao(escalao);
            boolean hasEscaloesModalidade = false;
            for (Escalao atletaEsca : atleta.getEscaloes()) {
                for (Escalao modalidadeEsca : modalidade.getEscaloes()){
                    if(modalidadeEsca.getId() == atletaEsca.getId()){
                        hasEscaloesModalidade = true;
                    }
                }
            }
            if(!hasEscaloesModalidade){
                atleta.removeModalidade(modalidade);
            }

            escalao.removeAtleta(atleta);
        }catch (MyEntityNotFoundException | MyIllegalArgumentException e){
            throw e;
        } catch (Exception e){
            throw new EJBException("ERROR_RETRIEVING_ENTITIES -> " + e.getMessage());
        }
    }
}
