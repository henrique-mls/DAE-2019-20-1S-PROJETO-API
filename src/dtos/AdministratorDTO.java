package dtos;

import java.io.Serializable;

public class AdministratorDTO extends UserDTO implements Serializable {
    public AdministratorDTO() {
    }

    public AdministratorDTO(String username, String name, String password, String email) {
        super(username, name, password, email);
    }
}
