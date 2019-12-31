package ws;

import dtos.AtletaDTO;
import dtos.SocioDTO;
import ejbs.AtletaBean;
import entities.Atleta;
import entities.Socio;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@DeclareRoles({"Administrador", "Atleta"})
@Path("/atletas") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class AtletaController {
    @EJB
    AtletaBean atletaBean;

    public static AtletaDTO toDTO(Atleta atleta){
        return new AtletaDTO(atleta.getUsername(),
                atleta.getName(),
                atleta.getPassword(),
                atleta.getEmail());
    }

    public static List<AtletaDTO> toDTOs(List<Atleta> atletas) {
        return atletas.stream().map(AtletaController::toDTO).collect(Collectors.toList());
    }

    @RolesAllowed("Administrador")
    @GET
    @Path("/") //"/api/atletas/"
    public List<AtletaDTO> all() {
        try {
            return toDTOs(atletaBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ATLETAS", e);
        }
    }

    @RolesAllowed({"Administrador", "Atleta"})
    @GET
    @Path("{username}")
    public Response getAtletaDetails(@PathParam("username") String username){
        try{
            Atleta atleta = atletaBean.findAtleta(username);
            if(atleta !=null){
                return Response.status(Response.Status.OK).entity(toDTO(atleta)).build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e){
            throw new EJBException("ERROR_GET_ATLETA_DETAILS", e);
        }
    }

    @RolesAllowed("Administrador")
    @POST
    @Path("/") //"/api/socios/"
    public Response createNewAtleta(AtletaDTO atletaDTO) throws MyEntityExistsException, MyConstraintViolationException {
        atletaBean.create(atletaDTO.getUsername(),atletaDTO.getName(),atletaDTO.getPassword(),atletaDTO.getEmail());
        Atleta atleta = atletaBean.findAtleta(atletaDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(toDTO(atleta)).build();
    }

    @RolesAllowed("Administrador")
    @PUT
    @Path("{username}")
    public Response updateAtleta(@PathParam("username") String username, AtletaDTO atletaDTO) throws MyEntityNotFoundException {
        atletaBean.update(username, atletaDTO.getName(), atletaDTO.getPassword(), atletaDTO.getEmail());
        Atleta atleta = atletaBean.findAtleta(username);
        return Response.status(Response.Status.OK).entity(toDTO(atleta)).build();
    }

    @RolesAllowed("Administrador")
    @DELETE
    @Path("{username}")
    public Response deleteAtleta(@PathParam("username") String username, AtletaDTO atletaDTO) throws MyEntityNotFoundException{
        atletaBean.remove(username);
        return Response.status(Response.Status.OK).build();
    }

    @RolesAllowed("Administrador")
    @PUT
    @Path("{username}/modalidades/enroll/{id}")
    public Response enrollAtleta(@PathParam("username") String username, @PathParam("id") int id) throws MyEntityNotFoundException, MyIllegalArgumentException {
        atletaBean.enrollAtletaInModalidade(username,id);
        return Response.status(Response.Status.OK).build();
    }

    @RolesAllowed("Administrador")
    @PUT
    @Path("{username}/modalidades/unroll/{id}")
    public Response unrollAtleta(@PathParam("username") String username, @PathParam("id") int id) throws MyEntityNotFoundException, MyIllegalArgumentException {
        atletaBean.unrollAtletaInModalidade(username,id);
        return Response.status(Response.Status.OK).build();
    }
}
