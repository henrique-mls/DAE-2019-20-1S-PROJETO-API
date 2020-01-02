package entities;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPresencas",
                query = "SELECT p FROM Presencas p ORDER BY p.id"
        )
})
public class Presencas {

    @Id
    private int id;
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "ATLETA_PRESENTE")
    @MapKeyColumn(name = "ATLETA")
    @Column(name = "PRESENTE")
    private Map<String,Boolean> mapaPresencas;
    private Date date;
    @ManyToOne
    private Escalao escalao;

    public Presencas() {
        this.mapaPresencas = new HashMap<String, Boolean>();
    }

    public Presencas(int id,Date date,Escalao escalao) {
        this.id = id;
        this.date = date;
        this.escalao = escalao;
        this.mapaPresencas = new HashMap<String, Boolean>();
    }

    public Escalao getEscalao() {
        return escalao;
    }

    public void setEscalao(Escalao escalao) {
        this.escalao = escalao;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, Boolean> getMapaPresencas() {
        return mapaPresencas;
    }

    public void setMapaPresencas(Map<String, Boolean> mapaPresencas) {
        this.mapaPresencas = mapaPresencas;
    }

    public void addPresenca(String username,Boolean presente){
        if(username == null || mapaPresencas.containsKey(username)){
            return;
        }
        mapaPresencas.put(username,presente);
    }

    public void removePresenca(String username){
        if(username == null || !mapaPresencas.containsKey(username)){
            return;
        }
        mapaPresencas.remove(username);
    }
}
