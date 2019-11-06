package entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@NamedQueries({
        @NamedQuery(
                name = "getAllAdministrators",
                query = "SELECT a FROM Administrador a ORDER BY a.name" // JPQL
        )
})

@Entity
public class Administrador extends User {
    public Administrador() {
    }

    public Administrador(String username, String name, String password, String email) {
        super(username, name, password, email);
    }
}
