package ws;

import dtos.AdministratorDTO;
import ejbs.AdministradorBean;
import entities.Administrador;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;

@DeclareRoles("Administrador")
@Path("/administradores") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class AdministradorController {
    @EJB
    AdministradorBean administradorBean;
    @Context
    private SecurityContext security;

    AdministratorDTO toDTO(Administrador administrador) {
        return new AdministratorDTO(
                administrador.getUsername(),
                administrador.getName(),
                administrador.getPassword(),
                administrador.getEmail()
        );
    }

    List<AdministratorDTO> toDTOs(List<Administrador> administradors) {
        return administradors.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @RolesAllowed("Administrador")
    @GET
    @Path("/") //"/api/administrators/"
    public List<AdministratorDTO> all() {
        try {
            return toDTOs(administradorBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ADMINISTRATORs", e);
        }
    }

    @RolesAllowed("Administrador")
    @GET
    @Path("{username}")
    public Response getAdministratorDetails(@PathParam("username") String username){
        try{
            Administrador administrador = administradorBean.findAdministrator(username);
            if(administrador !=null){
                return Response.status(Response.Status.OK).entity(toDTO(administrador)).build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e){
            throw new EJBException("ERROR_GET_ADMINISTRATOR_DETAILS", e);
        }
    }

    @RolesAllowed("Administrador")
    @POST
    @Path("/") //"/api/administrators/"
    public Response createNewAdministrator(AdministratorDTO administratorDTO) throws MyEntityExistsException, MyConstraintViolationException {
            administradorBean.create(administratorDTO.getUsername(),administratorDTO.getName(),administratorDTO.getPassword(),
                                    administratorDTO.getEmail());
            Administrador administrador = administradorBean.findAdministrator(administratorDTO.getUsername());
            return Response.status(Response.Status.CREATED).entity(toDTO(administrador)).build();
    }

    @RolesAllowed("Administrador")
    @PUT
    @Path("{username}")
    public Response updateAdministrator(@PathParam("username") String username, AdministratorDTO administratorDTO) throws MyEntityNotFoundException{
        administradorBean.update(username,
                administratorDTO.getName(),
                administratorDTO.getPassword(),
                administratorDTO.getEmail());
        Administrador administrador = administradorBean.findAdministrator(username);
        return Response.status(Response.Status.OK).entity(toDTO(administrador)).build();
    }

    @RolesAllowed("Administrador")
    @DELETE
    @Path("{username}")
    public Response deleteAdministrator(@PathParam("username") String username, AdministratorDTO administratorDTO) throws MyEntityNotFoundException{
        if(security.getUserPrincipal().getName().equals(username)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        administradorBean.remove(username);
        return Response.status(Response.Status.OK).build();
    }

}
