package ejbs;

import entities.Estado;
import entities.Presencas;
import entities.TipoProduto;
import entities.Treinador;

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
    @EJB
    private AtletaBean atletaBean;
    @EJB
    private TreinadorBean treinadorBean;
    @EJB
    private EscalaoBean escalaoBean;
    @EJB
    private PresencasBean presencasBean;
    @EJB
    private GraduacaoBean graduacaoBean;

    @PostConstruct
    public void populateDB() {
        try {
            administradorBean.create("1111111", "1111111", "1111111", "1111111@gmail.com");
            administradorBean.create("2222222", "2222222", "2222222", "2222222@gmail.com");
            administradorBean.create("3333333", "3333333", "3333333", "3333333@gmail.com");
            administradorBean.create("4444444", "4444444", "4444444", "4444444@gmail.com");
            administradorBean.create("admin1", "4444444", "123", "4444444@gmail.com");

            horarioBean.create(1,DayOfWeek.FRIDAY,3,2);

            modalidadeBean.create(1,"Esport");
            //modalidadeBean.enrollModalidadeInHorario(1,1);
            modalidadeBean.create(2,"Basket");
            //modalidadeBean.enrollModalidadeInHorario(2,1);

            produtoBean.create(1, TipoProduto.ARTIGO_DESPORTIVO,"descricao",25);
            produtoBean.create(2, TipoProduto.AULA,"descricao 2",2);

            socioBean.create("socio1", "Socio1", "socio1", "socio1@gmail.com");
            socioBean.create("socio2", "socio2", "socio2", "socio2@gmail.com");

            atletaBean.create("atleta1", "Atleta1", "atleta1", "atleta1@gmail.com");
            atletaBean.create("atleta2", "Atleta2", "atleta2", "atleta2@gmail.com");

            treinadorBean.create("treinador1","treinador1","treinador1","treinador1@gmail.com");

            pagamentoBean.create(1,"socio1",1,new Date(),2,99.99f, Estado.PAGO);
            pagamentoBean.create(2,"socio1",1,new Date(),3,100.99f, Estado.NAO_PAGO);

            escalaoBean.create(1,"juniores",1);
            escalaoBean.enrollHorarioInEscalao(1,1);

            //modalidadeBean.enrollEscalaoInModalidade(1,1);
            treinadorBean.enrollTreinadorInEscalaoInModalidade("treinador1",1,1);
            treinadorBean.enrollTreinadorInHorario("treinador1",1);

            atletaBean.enrollAtletaInHorario("atleta1",1);
            //atletaBean.enrollAtletaInModalidade("atleta1",1);

            presencasBean.create(1,"2020-01-02",1);

            presencasBean.addOrUpdatePresencaInPresencas(1,"atleta1",true);
            presencasBean.addOrUpdatePresencaInPresencas(1,"atleta2",false);

            graduacaoBean.create(1,"graduacao1","desc1");
            graduacaoBean.create(2,"graduacao2","desc2");
            graduacaoBean.create(3,"graduacao3","desc3");
            graduacaoBean.create(4,"graduacao4","desc4");
            graduacaoBean.create(5,"graduacao5","desc5");

            graduacaoBean.enrollAtletaInGraduacao("atleta1",1);

        } catch (Exception e) {
            throw new EJBException("Error: " + e.getMessage());
        }
    }
}