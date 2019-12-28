package dtos;

import java.io.Serializable;

public class EmailDTO implements Serializable {
    private String subject;
    private String message;

    public EmailDTO() {
    }

    public EmailDTO(String subject, String message) {
        this.subject = subject;
        this.message = message;
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
}