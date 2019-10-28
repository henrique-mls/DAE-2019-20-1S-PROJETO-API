package ws;

import dtos.AdministratorDTO;
import ejbs.AdministratorBean;
import entities.Administrator;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/administrators") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class AdministratorController {
    @EJB
    AdministratorBean administratorBean;

    AdministratorDTO toDTO(Administrator administrator) {
        return new AdministratorDTO(
                administrator.getUsername(),
                administrator.getPassword(),
                administrator.getName(),
                administrator.getEmail()
        );
    }

    List<AdministratorDTO> toDTOs(List<Administrator> students) {
        return students.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/") //"/api/administrators/"
    public List<AdministratorDTO> all() {
        try {
            return toDTOs(administratorBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_STUDENTS", e);
        }
    }
}
