package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String subject;
    private String message;

    public Mensagem(String subject, String message){
        this.subject = subject;
        this.message = message;
    }

    public Mensagem() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }
}
