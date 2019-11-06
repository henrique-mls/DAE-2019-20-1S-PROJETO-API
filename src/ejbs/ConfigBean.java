package ejbs;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.annotation.PostConstruct;


@Singleton(name = "ConfigEJB")
@Startup
public class ConfigBean {
    @EJB
    private AdministradorBean administradorBean;

    @PostConstruct
    public void populateDB() {
        try {
            administradorBean.create("1111111", "charles cricket", "1111111", "1111111@gmail.com");
            administradorBean.create("2222222", "oracle 11g", "2222222", "2222222@gmail.com");
            administradorBean.create("3333333", "olga craveiro", "3333333", "3333333@gmail.com");
            administradorBean.create("4444444", "george clooney", "4444444", "4444444@gmail.com");

        } catch (Exception e) {
            throw new EJBException("Error: " + e.getMessage());
        }
    }
}