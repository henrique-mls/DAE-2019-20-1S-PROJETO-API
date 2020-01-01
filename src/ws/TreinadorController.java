package ws;

import dtos.AtletaDTO;
import dtos.EscalaoDTO;
import dtos.ModalidadeDTO;
import dtos.TreinadorDTO;
import ejbs.ModalidadeBean;
import ejbs.TreinadorBean;
import entities.*;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@DeclareRoles({"Administrador", "Treinador"})
@Path("/treinadores") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class TreinadorController {
    @EJB
    TreinadorBean treinadorBean;
    @Context
    private SecurityContext security;

    public static TreinadorDTO toDTO(Treinador treinador) {
        return new TreinadorDTO(
                treinador.getUsername(),
                treinador.getName(),
                treinador.getPassword(),
                treinador.getEmail()
        );
    }

    public static TreinadorDTO toDTOWithLists(Treinador treinador) {
        TreinadorDTO treinadorDTO =  new TreinadorDTO(
                treinador.getUsername(),
                treinador.getName(),
                treinador.getPassword(),
                treinador.getEmail()
        );
        List<ModalidadeDTO> modalidadeDTOS = ModalidadeController.toDTOs(treinador.getModalidades());
        List<Atleta> atletas = new ArrayList<>();
        List<Horario> horarios = new ArrayList<>();

        for (Escalao escalao : treinador.getEscaloes()) {
            atletas.addAll(escalao.getAtletas());
            horarios.addAll(escalao.getHorarios());
        }
        List<EscalaoDTO> escalaoDTOS = EscalaoController.toDTOs(treinador.getEscaloes());
        List<AtletaDTO> atletaDTOS = AtletaController.toDTOs(atletas);
        treinadorDTO.setModalidades(modalidadeDTOS);
        treinadorDTO.setAtletas(atletaDTOS);
        treinadorDTO.setHorarios(horarios);
        treinadorDTO.setEscaloes(escalaoDTOS);

        return treinadorDTO;
    }

    public static List<TreinadorDTO> toDTOs(List<Treinador> treinadores) {
        return treinadores.stream().map(TreinadorController::toDTO).collect(Collectors.toList());
    }

    @RolesAllowed("Administrador")
    @GET
    @Path("/") //"/api/treinadores/"
    public List<TreinadorDTO> all() {
        try {
            return toDTOs(treinadorBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_TREINADORES", e);
        }
    }

    @RolesAllowed({"Administrador", "Treinador"})
    @GET
    @Path("{username}")
    public Response getTreinadorDetails(@PathParam("username") String username){
        try{
            Treinador treinador = treinadorBean.findTreinador(username);
            if(security.isUserInRole("Socio")){
                if(security.getUserPrincipal().getName().equals(username)){
                    if(treinador !=null){
                        return Response.status(Response.Status.OK).entity(toDTO(treinador)).build();
                    }
                }else{
                    return Response.status(Response.Status.UNAUTHORIZED).build();
                }
            }else{
                if(treinador !=null){
                    return Response.status(Response.Status.OK).entity(toDTO(treinador)).build();
                }
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e){
            throw new EJBException("ERROR_GET_TREINADOR_DETAILS", e);
        }
    }
    @RolesAllowed("Administrador")
    @POST
    @Path("/") //"/api/treinadores/"
    public Response createNewTreinador(TreinadorDTO treinadorDTO) throws MyEntityExistsException, MyConstraintViolationException {
        treinadorBean.create(treinadorDTO.getUsername(),treinadorDTO.getName(),treinadorDTO.getPassword(),treinadorDTO.getEmail());
        Treinador treinador = treinadorBean.findTreinador(treinadorDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(toDTO(treinador)).build();
    }

    @RolesAllowed("Administrador")
    @PUT
    @Path("{username}")
    public Response updateTreinador(@PathParam("username") String username, TreinadorDTO treinadorDTO) throws MyEntityNotFoundException {
        treinadorBean.update(username, treinadorDTO.getName(), treinadorDTO.getPassword(), treinadorDTO.getEmail());
        Treinador treinador = treinadorBean.findTreinador(username);
        return Response.status(Response.Status.OK).entity(toDTO(treinador)).build();
    }

    @RolesAllowed("Administrador")
    @DELETE
    @Path("{username}")
    public Response deleteTreinador(@PathParam("username") String username, TreinadorDTO treinadorDTO) throws MyEntityNotFoundException{
        treinadorBean.remove(username);
        return Response.status(Response.Status.OK).build();
    }

    @RolesAllowed("Administrador")
    @PUT
    @Path("{username}/modalidades/enroll/{modalidadeId}/escalao/{escalaoId}")
    public Response enrollTreinadorInEscalaoInModalidade(@PathParam("username") String username, @PathParam("escalaoId") int escalaoId,@PathParam("modalidadeId") int modalidadeId) throws MyEntityNotFoundException, MyIllegalArgumentException {
        treinadorBean.enrollTreinadorInEscalaoInModalidade(username,escalaoId,modalidadeId);
        return Response.status(Response.Status.OK).build();
    }

    @RolesAllowed("Administrador")
    @PUT
    @Path("{username}/modalidades/unroll/{modalidadeId}/escalao/{escalaoId}")
    public Response unrollTreinador(@PathParam("username") String username, @PathParam("modalidadeId") int modalidadeId,@PathParam("escalaoId") int escalaoId) throws MyEntityNotFoundException, MyIllegalArgumentException {
        treinadorBean.unrollTreinadorInEscalaoInModalidade(username,escalaoId,modalidadeId);
        return Response.status(Response.Status.OK).build();
    }
}
