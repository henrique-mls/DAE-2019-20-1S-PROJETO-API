package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Recibo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String filepath;

    @NotNull
    private String fileName;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Pagamento pagamento;

    public Recibo() {
    }

    public Recibo(String filepath, String fileName, Pagamento pagamento) {
        this.filepath = filepath;
        this.fileName = fileName;
        this.pagamento = pagamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
}
