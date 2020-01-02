package ws;

import dtos.*;
import ejbs.AtletaBean;
import ejbs.EmailBean;
import ejbs.TreinadorBean;
import entities.*;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;
import jwt.Jwt;

import javax.accessibility.AccessibleText;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@DeclareRoles({"Administrador", "Atleta", "Treinador"})
@Path("/atletas") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class AtletaController {
    @EJB
    AtletaBean atletaBean;
    @Context
    private SecurityContext security;
    @EJB
    EmailBean emailBean;
    @EJB
    TreinadorBean treinadorBean;

    public static AtletaDTO toDTO(Atleta atleta){
        return new AtletaDTO(atleta.getUsername(),
                atleta.getName(),
                atleta.getPassword(),
                atleta.getEmail());
    }
    public static AtletaDTO toDTOWithLists(Atleta atleta) {
        AtletaDTO atletaDTO =  new AtletaDTO(
                atleta.getUsername(),
                atleta.getName(),
                atleta.getPassword(),
                atleta.getEmail()
        );
        List<ModalidadeDTO> modalidadeDTOS = ModalidadeController.toDTOs(atleta.getModalidades());
        List<PagamentoDTO> pagamentoDTOS = PagamentoController.toDTOs(atleta.getPagamentos());
        List<Treinador> treinadores = new ArrayList<>();
        List<Horario> horarios = new ArrayList<>();

        for (Escalao escalao : atleta.getEscaloes()) {
            treinadores.addAll(escalao.getTreinadores());
            horarios.addAll(escalao.getHorarios());
        }
        List<EscalaoDTO> escalaoDTOS = EscalaoController.toDTOs(atleta.getEscaloes());
        List<TreinadorDTO> treinadorDTOS = TreinadorController.toDTOs(treinadores);
        atletaDTO.setModalidades(modalidadeDTOS);
        atletaDTO.setTreinadores(treinadorDTOS);
        atletaDTO.setHorarios(horarios);
        atletaDTO.setEscaloes(escalaoDTOS);
        atletaDTO.setPagamentos(pagamentoDTOS);

        return atletaDTO;
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
            if(security.isUserInRole("Atleta")){
                if(security.getUserPrincipal().getName().equals(username)){
                    if(atleta !=null){
                        return Response.status(Response.Status.OK).entity(toDTOWithLists(atleta)).build();
                    }
                }else{
                    return Response.status(Response.Status.UNAUTHORIZED).build();
                }
            }else{
                if(atleta !=null){
                    return Response.status(Response.Status.OK).entity(toDTOWithLists(atleta)).build();
                }
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
    @Path("{username}/modalidades/enroll/{modalidadeId}/escalao/{escalaoId}")
    public Response enrollAtleta(@PathParam("username") String username, @PathParam("modalidadeId") int modalidadeId,@PathParam("escalaoId") int escalaoID) throws MyEntityNotFoundException, MyIllegalArgumentException {
        atletaBean.enrollAtletaInEscalaoInModalidade(username,escalaoID,modalidadeId);
        return Response.status(Response.Status.OK).build();
    }

    @RolesAllowed("Administrador")
    @PUT
    @Path("{username}/modalidades/unroll/{modalidadeId}/escalao/{escalaoId}")
    public Response unrollAtleta(@PathParam("username") String username, @PathParam("modalidadeId") int modalidadeId, @PathParam("escalaoId") int escalaoId) throws MyEntityNotFoundException, MyIllegalArgumentException {
        atletaBean.unrollAtletaInEscalaoInModalidade(username,escalaoId,modalidadeId);
        return Response.status(Response.Status.OK).build();
    }

    @RolesAllowed({"Administrador", "Treinador"})
    @POST
    @Path("{username}/email/send")
    public Response sendEmailToAtleta(@PathParam("username") String username, EmailDTO emailDTO) throws MessagingException {
        Atleta atleta = atletaBean.findAtleta(username);
        if(atleta != null){
            if(security.isUserInRole("Treinador")){
                Treinador treinador = treinadorBean.findTreinador(security.getUserPrincipal().getName());
                if(treinador != null){
                    for (Escalao escalao : treinador.getEscaloes()) {
                        if(escalao.getAtletas().contains(atleta)){
                            emailBean.send(atleta.getEmail(), emailDTO.getSubject(), emailDTO.getMessage());
                            return Response.status(Response.Status.OK).build();
                        }
                    }
                }else{
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Treinador with username " + username + " not found.").build();
                }
            }else{
                emailBean.send(atleta.getEmail(), emailDTO.getSubject(), emailDTO.getMessage());
                return Response.status(Response.Status.OK).build();
            }
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Atleta with username " + username + " not found.").build();
    }
}
