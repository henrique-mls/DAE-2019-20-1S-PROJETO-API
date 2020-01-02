package ws;

import dtos.PresencasDTO;
import dtos.PresencasDTO;
import ejbs.EscalaoBean;
import ejbs.PresencasBean;
import entities.Escalao;
import entities.Presencas;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Path("/presencas") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class PresencaController {

    @EJB
    PresencasBean presencasBean;

    public static PresencasDTO toDTO(Presencas presencas) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        PresencasDTO presencasDTO = new PresencasDTO(presencas.getId(),dateFormat.format(presencas.getDate()),
                presencas.getEscalao().getId());
        presencasDTO.setMapaPresencas(presencas.getMapaPresencas());
        return presencasDTO;
    }

    public  static List<PresencasDTO> toDTOs(List<Presencas> presencas) {
        return presencas.stream().map(PresencaController::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/") //"/api/escalaos/"
    public List<PresencasDTO> all() {
        try {
            return toDTOs(presencasBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_MODALIDADES", e);
        }
    }
    @GET
    @Path("{id}")
    public Response getPresencasDetails(@PathParam("id") int id){
        try{
            Presencas presencas = presencasBean.findPresencas(id);
            if(presencas !=null){
                return Response.status(Response.Status.OK).entity(toDTO(presencas)).build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e){
            throw new EJBException("ERROR_GET_MODALIDADE_DETAILS", e);
        }
    }
    @POST
    @Path("/")
    public Response createNewPresenca(PresencasDTO presencasDTO) throws MyEntityExistsException, MyConstraintViolationException {
        presencasBean.create(presencasDTO.getId(),presencasDTO.getDate(),presencasDTO.getEscalaoID());
        Presencas presencas = presencasBean.findPresencas(presencasDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(presencas)).build();
    }

    @PUT
    @Path("{id}")
    public Response updatePresencas(@PathParam("id") int id, PresencasDTO presencasDTO) throws MyEntityNotFoundException {
        presencasBean.update(id,
                presencasDTO.getDate(),
                presencasDTO.getEscalaoID());
        Presencas presencas = presencasBean.findPresencas(id);
        return Response.status(Response.Status.OK).entity(toDTO(presencas)).build();
    }

    @PUT
    @Path("{presencasID}/addOrUpdate/alteta/{username}/{presente}")
    public Response addOrUpdatePresencaInPresencas(@PathParam("presencasID") int presencasID,
                                                   @PathParam("username") String username,@PathParam("presente") Boolean presente) throws MyEntityNotFoundException, MyIllegalArgumentException {
        presencasBean.addOrUpdatePresencaInPresencas(presencasID,username,presente);
        return Response.status(Response.Status.OK).build();
    }
    @PUT
    @Path("{presencasID}/remove/alteta/{username}")
    public Response removePresencaInPresencas(@PathParam("presencasID") int presencasID,
                                                   @PathParam("username") String username) throws MyEntityNotFoundException, MyIllegalArgumentException {
        presencasBean.removePresencaInPresencas(presencasID,username);
        return Response.status(Response.Status.OK).build();
    }
}
