package entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@NamedQueries({
        @NamedQuery(
                name = "getAllAdministrators",
                query = "SELECT a FROM Administrator a ORDER BY a.name" // JPQL
        )
})

@Entity
public class Administrator extends User {
    public Administrator() {
    }

    public Administrator(String username, String name, String password, String email) {
        super(username, name, password, email);
    }
}
