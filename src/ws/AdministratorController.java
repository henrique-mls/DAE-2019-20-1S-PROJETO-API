package ws;

import dtos.AdministratorDTO;
import ejbs.AdministratorBean;
import entities.Administrator;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    @POST
    @Path("/") //"/api/administrators/"
    public Response createNewAdministrator(AdministratorDTO administratorDTO) {
        try {
            administratorBean.create(administratorDTO.getUsername(),administratorDTO.getName(),administratorDTO.getPassword(),
                                    administratorDTO.getEmail());
            Administrator administrator = administratorBean.findAdministrator(administratorDTO.getUsername());
            if(administrator != null){
                return Response.status(Response.Status.CREATED).entity(toDTO(administrator)).build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_STUDENTS", e);
        }
    }
}
