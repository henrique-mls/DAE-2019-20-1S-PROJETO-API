package ws;


import dtos.HorarioDTO;
import dtos.HorarioDTO;
import ejbs.HorarioBean;
import entities.Escalao;
import entities.Horario;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/horarios") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class HorarioController {

    @EJB
    HorarioBean horarioBean;

    public static HorarioDTO toDTO(Horario horario) {
        HorarioDTO escalaoDTO = new  HorarioDTO(horario.getId(),horario.getDia(),horario.getHoraInicio(),horario.getDuracao());
        return escalaoDTO;
    }

    @POST
    @Path("/") //"/api/escalaos/"
    public Response createNewHorario(HorarioDTO horarioDTO) throws MyEntityExistsException, MyConstraintViolationException {
        horarioBean.create(horarioDTO.getId(),horarioDTO.getDia(),horarioDTO.getHoraInicio(),horarioDTO.getDuracao());
        Horario horario = horarioBean.findHorario(horarioDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(horario)).build();
    }


    @RolesAllowed("Administrador")
    @DELETE
    @Path("{id}")
    public Response deleteHorario(@PathParam("id") int id) throws MyEntityNotFoundException{
        horarioBean.remove(id);
        return Response.status(Response.Status.OK).build();
    }

}
