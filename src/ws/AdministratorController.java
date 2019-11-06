package ws;

import dtos.AdministratorDTO;
import ejbs.AdministratorBean;
import entities.Administrator;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

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
                administrator.getName(),
                administrator.getPassword(),
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

    @GET
    @Path("{username}")
    public Response getAdministratorDetails(@PathParam("username") String username){
        try{
            Administrator administrator = administratorBean.findAdministrator(username);
            if(administrator!=null){
                return Response.status(Response.Status.OK).entity(toDTO(administrator)).build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e){
            throw new EJBException("ERROR_GET_ADMINISTRATOR_DETAILS", e);
        }
    }

    @POST
    @Path("/") //"/api/administrators/"
    public Response createNewAdministrator(AdministratorDTO administratorDTO) throws MyEntityExistsException, MyConstraintViolationException {
            administratorBean.create(administratorDTO.getUsername(),administratorDTO.getName(),administratorDTO.getPassword(),
                                    administratorDTO.getEmail());
            Administrator administrator = administratorBean.findAdministrator(administratorDTO.getUsername());
            return Response.status(Response.Status.CREATED).entity(toDTO(administrator)).build();
    }


    @PUT
    @Path("{username}")
    public Response updateAdministrator(@PathParam("username") String username, AdministratorDTO administratorDTO) throws MyEntityNotFoundException{
        administratorBean.update(username,
                administratorDTO.getName(),
                administratorDTO.getPassword(),
                administratorDTO.getEmail());
        Administrator administrator = administratorBean.findAdministrator(username);
        return Response.status(Response.Status.OK).entity(toDTO(administrator)).build();
    }

    @DELETE
    @Path("{username}")
    public Response deleteAdministrator(@PathParam("username") String username, AdministratorDTO administratorDTO) throws MyEntityNotFoundException{
        administratorBean.remove(username);
        return Response.status(Response.Status.OK).build();
    }

}
