package ws;

import dtos.*;
import ejbs.ModalidadeBean;
import entities.Escalao;
import entities.Horario;
import entities.Modalidade;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@DeclareRoles({"Administrador", "Treinador"})
@Path("/modalidades") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ModalidadeController {

    @EJB
    ModalidadeBean modalidadeBean;

    public static ModalidadeDTO toDTO(Modalidade modalidade) {
        ModalidadeDTO modalidadeDTO = new  ModalidadeDTO(
                modalidade.getId(),
                modalidade.getNome()
        );

        return modalidadeDTO;
    }

    ModalidadeDTO toDTOWithLists(Modalidade modalidade) {
        ModalidadeDTO modalidadeDTO = new  ModalidadeDTO(
                modalidade.getId(),
                modalidade.getNome()
        );
        List<TreinadorDTO> treinadorDTOS = new ArrayList<>();
        List<AtletaDTO> atletaDTOS = new ArrayList<>();
        for (Escalao escalao : modalidade.getEscaloes()) {
            modalidadeDTO.setHorarios(escalao.getHorarios());
            treinadorDTOS.addAll(TreinadorController.toDTOs(escalao.getTreinadores()));
            atletaDTOS.addAll(AtletaController.toDTOs(escalao.getAtletas()));
        }
        List<EscalaoDTO> escalaoDTOS = EscalaoController.toDTOs(modalidade.getEscaloes());
        modalidadeDTO.setEscaloes(escalaoDTOS);
        modalidadeDTO.setTreinadores(treinadorDTOS);
        modalidadeDTO.setAtletas(atletaDTOS);
        return modalidadeDTO;
    }

    public  static List<ModalidadeDTO> toDTOs(List<Modalidade> modalidades) {
        return modalidades.stream().map(ModalidadeController::toDTO).collect(Collectors.toList());
    }

    @RolesAllowed("Administrador")
    @GET
    @Path("/") //"/api/modalidades/"
    public List<ModalidadeDTO> all() {
        try {
            return toDTOs(modalidadeBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_MODALIDADES", e);
        }
    }

    @RolesAllowed({"Administrador", "Treinador"})
    @GET
    @Path("{id}")
    public Response getModalidadesDetails(@PathParam("id") int id){
        try{
            Modalidade modalidade = modalidadeBean.findModalidade(id);
            if(modalidade !=null){
                return Response.status(Response.Status.OK).entity(toDTOWithLists(modalidade)).build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e){
            throw new EJBException("ERROR_GET_MODALIDADE_DETAILS", e);
        }
    }

    @RolesAllowed("Administrador")
    @POST
    @Path("/") //"/api/modalidades/"
    public Response createNewModalidade(ModalidadeDTO modalidadeDTO) throws MyEntityExistsException, MyConstraintViolationException {
        modalidadeBean.create(modalidadeDTO.getId(),modalidadeDTO.getNome());
        Modalidade modalidade = modalidadeBean.findModalidade(modalidadeDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(modalidade)).build();
    }

    @RolesAllowed("Administrador")
    @PUT
    @Path("{id}")
    public Response updateModalide(@PathParam("id") int id, ModalidadeDTO modalidadeDTO) throws MyEntityNotFoundException {
        modalidadeBean.update(id,
                modalidadeDTO.getNome());
        Modalidade modalidade = modalidadeBean.findModalidade(id);
        return Response.status(Response.Status.OK).entity(toDTO(modalidade)).build();
    }

    @RolesAllowed("Administrador")
    @DELETE
    @Path("{id}")
    public Response deleteModalidade(@PathParam("id") int id,ModalidadeDTO modalidadeDTO) throws MyEntityNotFoundException{
        modalidadeBean.remove(id);
        return Response.status(Response.Status.OK).build();
    }

    @RolesAllowed("Administrador")
    @PUT
    @Path("{modalidadeId}/escaloes/enroll/{escalaoId}")
    public Response enrollEscalaoInModalidade(@PathParam("escalaoId") int escalaoId,@PathParam("modalidadeId") int modalidadeId) throws MyEntityNotFoundException, MyIllegalArgumentException {
        modalidadeBean.enrollEscalaoInModalidade(escalaoId,modalidadeId);
        return Response.status(Response.Status.OK).build();
    }

    @RolesAllowed("Administrador")
    @PUT
    @Path("{modalidadeId}/escaloes/unroll/{escalaoId}")
    public Response unrollEscalaoInModalidade(@PathParam("modalidadeId") int modalidadeId,@PathParam("escalaoId") int escalaoId) throws MyEntityNotFoundException, MyIllegalArgumentException {
        modalidadeBean.unrollEscalaoInModalidade(escalaoId,modalidadeId);
        return Response.status(Response.Status.OK).build();
    }
}
