package ejbs;

import entities.Estado;
import entities.TipoProduto;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.time.DayOfWeek;
import java.util.Date;


@Singleton(name = "ConfigEJB")
@Startup
public class ConfigBean {
    @EJB
    private AdministradorBean administradorBean;
    @EJB
    private ModalidadeBean modalidadeBean;
    @EJB
    private HorarioBean horarioBean;
    @EJB
    private ProdutoBean produtoBean;
    @EJB
    private PagamentoBean pagamentoBean;
    @EJB
    private SocioBean socioBean;

    @PostConstruct
    public void populateDB() {
        try {
            administradorBean.create("1111111", "1111111", "1111111", "1111111@gmail.com");
            administradorBean.create("2222222", "2222222", "2222222", "2222222@gmail.com");
            administradorBean.create("3333333", "3333333", "3333333", "3333333@gmail.com");
            administradorBean.create("4444444", "4444444", "4444444", "4444444@gmail.com");

            horarioBean.create(1,DayOfWeek.FRIDAY,3,2);
//            Horario horario = new Horario(1,DayOfWeek.FRIDAY,3,2);

            modalidadeBean.create(1,"Esport");
            modalidadeBean.enrollModalidadeInHorario(1,1);
//            modalidadeBean.create(2,"basket",horario);
//            modalidadeBean.create(3,"tenis",horario);
            produtoBean.create(1, TipoProduto.ARTIGO_DESPORTIVO,"descricao",25);
            produtoBean.create(2, TipoProduto.AULA,"descricao 2",2);

            socioBean.create("socio1", "Socio1", "socio1", "socio1@gmail.com");
            socioBean.create("socio2", "socio2", "socio2", "socio2@gmail.com");

            pagamentoBean.create(1,"socio1",1,new Date(),2,99.99f, Estado.PAGO);
            //pagamentoBean.create(2,"2222222",1,new Date(),2,99.99f, Estado.PAGO);


        } catch (Exception e) {
            throw new EJBException("Error: " + e.getMessage());
        }
    }
}