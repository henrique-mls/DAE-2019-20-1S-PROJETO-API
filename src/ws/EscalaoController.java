package ws;

import dtos.AtletaDTO;
import dtos.EscalaoDTO;
import dtos.PresencasDTO;
import dtos.TreinadorDTO;
import ejbs.EscalaoBean;
import entities.Escalao;
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

@DeclareRoles({"Administrador", "Treinador"})
@Path("/escaloes") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class EscalaoController {

    @EJB
    EscalaoBean escalaoBean;

    public static EscalaoDTO toDTO(Escalao escalao) {
        EscalaoDTO escalaoDTO = new EscalaoDTO();
        if(escalao.getModalidade() != null){
            escalaoDTO = new  EscalaoDTO(escalao.getId(), escalao.getNome(), escalao.getModalidade().getId());
        }else {
            escalaoDTO = new  EscalaoDTO(escalao.getId(), escalao.getNome(),-1);
        }

        return escalaoDTO;
    }

    EscalaoDTO toDTOWithLists(Escalao escalao) {
        EscalaoDTO escalaoDTO = new EscalaoDTO();
        if(escalao.getModalidade() != null){
            escalaoDTO = new  EscalaoDTO(escalao.getId(), escalao.getNome(), escalao.getModalidade().getId());
        }else {
            escalaoDTO = new  EscalaoDTO(escalao.getId(), escalao.getNome(),-1);
        }
        escalaoDTO.setHorarios(escalao.getHorarios());
        List<TreinadorDTO> treinadorDTOS = TreinadorController.toDTOs(escalao.getTreinadores());
        List<AtletaDTO> atletaDTOS = AtletaController.toDTOs(escalao.getAtletas());
        List<PresencasDTO> presencasDTOS = PresencaController.toDTOs(escalao.getPresencas());
        escalaoDTO.setTreinadores(treinadorDTOS);
        escalaoDTO.setAtletas(atletaDTOS);
        escalaoDTO.setPresencas(presencasDTOS);
        return escalaoDTO;
    }

    public  static List<EscalaoDTO> toDTOs(List<Escalao> escalaos) {
        return escalaos.stream().map(EscalaoController::toDTO).collect(Collectors.toList());
    }

    @RolesAllowed("Administrador")
    @GET
    @Path("/") //"/api/escaloes/"
    public List<EscalaoDTO> all() {
        try {
            return toDTOs(escalaoBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_MODALIDADES", e);
        }
    }

    @RolesAllowed({"Administrador", "Treinador"})
    @GET
    @Path("{id}")
    public Response getEscalaosDetails(@PathParam("id") int id){
        try{
            Escalao escalao = escalaoBean.findEscalao(id);
            if(escalao !=null){
                return Response.status(Response.Status.OK).entity(toDTOWithLists(escalao)).build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e){
            throw new EJBException("ERROR_GET_ESCALAO_DETAILS", e);
        }
    }

    @RolesAllowed("Administrador")
    @POST
    @Path("/") //"/api/escalaos/"
    public Response createNewEscalao(EscalaoDTO escalaoDTO) throws MyEntityExistsException, MyConstraintViolationException {
        escalaoBean.create(escalaoDTO.getId(),escalaoDTO.getNome(),escalaoDTO.getModalidadeID());
        Escalao escalao = escalaoBean.findEscalao(escalaoDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(escalao)).build();
    }

    @RolesAllowed("Administrador")
    @PUT
    @Path("{id}")
    public Response updateModalide(@PathParam("id") int id, EscalaoDTO escalaoDTO) throws MyEntityNotFoundException {
        escalaoBean.update(id,
                escalaoDTO.getNome(),
                escalaoDTO.getModalidadeID());
        Escalao escalao = escalaoBean.findEscalao(id);
        return Response.status(Response.Status.OK).entity(toDTO(escalao)).build();
    }

    @RolesAllowed("Administrador")
    @DELETE
    @Path("{id}")
    public Response deleteEscalao(@PathParam("id") int id) throws MyEntityNotFoundException{
        escalaoBean.remove(id);
        return Response.status(Response.Status.OK).build();
    }

    @RolesAllowed("Administrador")
    @PUT
    @Path("{id}/horarios/enroll/{horarioID}")
    public Response enrollHorarioInEscalao(@PathParam("id") int id,@PathParam("horarioID") int horarioID) throws MyEntityNotFoundException, MyIllegalArgumentException {
        escalaoBean.enrollHorarioInEscalao(id,horarioID);
        return Response.status(Response.Status.OK).build();
    }
    @RolesAllowed("Administrador")
    @PUT
    @Path("{id}/horarios/unroll/{horarioID}")
    public Response unrollHorarioInEscalao(@PathParam("id") int id,@PathParam("horarioID") int horarioID) throws MyEntityNotFoundException, MyIllegalArgumentException {
        escalaoBean.unrollHorarioInEscalao(id,horarioID);
        return Response.status(Response.Status.OK).build();
    }
}
