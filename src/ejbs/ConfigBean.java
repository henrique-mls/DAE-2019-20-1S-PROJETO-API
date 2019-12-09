package ejbs;

import entities.Horario;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.annotation.PostConstruct;
import java.time.DayOfWeek;
import java.util.Date;


@Singleton(name = "ConfigEJB")
@Startup
public class ConfigBean {
    @EJB
    private AdministradorBean administradorBean;
    @EJB
    private ModalidadeBean modalidadeBean;

    @PostConstruct
    public void populateDB() {
        try {
            administradorBean.create("1111111", "1111111", "1111111", "1111111@gmail.com");
            administradorBean.create("2222222", "2222222", "2222222", "2222222@gmail.com");
            administradorBean.create("3333333", "3333333", "3333333", "3333333@gmail.com");
            administradorBean.create("4444444", "4444444", "4444444", "4444444@gmail.com");


            Horario horario = new Horario(1,DayOfWeek.FRIDAY,3,2);

            modalidadeBean.create(1,"Esport",horario);
            modalidadeBean.create(2,"basket",horario);
            modalidadeBean.create(3,"tenis",horario);

        } catch (Exception e) {
            throw new EJBException("Error: " + e.getMessage());
        }
    }
}