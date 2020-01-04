package ws;

import dtos.AtletaDTO;
import dtos.GraduacaoDTO;
import ejbs.GraduacaoBean;
import entities.Atleta;
import entities.Escalao;
import entities.Graduacao;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;


@Path("/graduacoes") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class GraduacaoController {
    @EJB
    GraduacaoBean graduacaoBean;

    public static GraduacaoDTO toDTO(Graduacao graduacao) {
        GraduacaoDTO graduacaoDTO = new GraduacaoDTO(graduacao.getId(),graduacao.getNome(),graduacao.getDescricao());
        return graduacaoDTO;
    }


    public static List<GraduacaoDTO> toDTOs(List<Graduacao> graduacoes) {
        return graduacoes.stream().map(GraduacaoController::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/") //"/api/graduacoes/"
    public List<GraduacaoDTO> all() {
        try {
            return toDTOs(graduacaoBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_GRADUACOES", e);
        }
    }

    @PermitAll
    @POST
    @Path("/")
    public Response newGraduacao(GraduacaoDTO graduacaoDTO) throws MyEntityExistsException, MyConstraintViolationException {
        graduacaoBean.create(graduacaoDTO.getId(),graduacaoDTO.getNome(),graduacaoDTO.getDescricao());
        Graduacao graduacao = graduacaoBean.findGraduacao(graduacaoDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(graduacao)).build();
    }


    @DELETE
    @Path("{id}")
    public Response deleteGraduacao(@PathParam("id") int id) throws MyEntityNotFoundException {
        graduacaoBean.remove(id);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("{id}/atletas/enroll/{username}")
    public Response enrollHorarioInEscalao(@PathParam("id") int id,@PathParam("username") String username) throws MyEntityNotFoundException, MyIllegalArgumentException {
        graduacaoBean.enrollAtletaInGraduacao(username,id);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("{id}/atletas/unroll/{username}")
    public Response unrollHorarioInEscalao(@PathParam("id") int id,@PathParam("username") String username) throws MyEntityNotFoundException, MyIllegalArgumentException {
        graduacaoBean.unrollAtletaInGraduacao(username,id);
        return Response.status(Response.Status.OK).build();
    }
}
