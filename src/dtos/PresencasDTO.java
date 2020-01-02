package dtos;

import entities.Atleta;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PresencasDTO implements Serializable {

    private int id;
    private Map<String,Boolean> mapaPresencas;
    private String date;
    private int escalaoID;


    public PresencasDTO() {
        this.mapaPresencas = new HashMap<String,Boolean>();
    }

    public PresencasDTO(int id,String date,int escalaoID) {
        this.id = id;
        this.date  = date;
        this.escalaoID = escalaoID;
        this.mapaPresencas = new HashMap<String,Boolean>();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEscalaoID() {
        return escalaoID;
    }

    public void setEscalaoID(int escalaoID) {
        this.escalaoID = escalaoID;
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
}
