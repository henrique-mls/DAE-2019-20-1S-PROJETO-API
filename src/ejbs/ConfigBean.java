package ejbs;

import entities.*;

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
    @EJB
    private HorarioBean horarioBean;
    @EJB
    private ProdutoBean produtoBean;
    @EJB
    private PagamentoBean pagamentoBean;

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

            pagamentoBean.create(1,"1111111",1,new Date(),2,99.99f, Estado.PAGO);

        } catch (Exception e) {
            throw new EJBException("Error: " + e.getMessage());
        }
    }
}