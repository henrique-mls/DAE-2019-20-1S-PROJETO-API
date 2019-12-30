package ws;

import dtos.AdministratorDTO;
import dtos.AtletaDTO;
import dtos.ModalidadeDTO;
import dtos.TreinadorDTO;
import ejbs.ModalidadeBean;
import entities.Horario;
import entities.Modalidade;
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


@Path("/modalidades") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ModalidadeController {

    @EJB
    ModalidadeBean modalidadeBean;

    ModalidadeDTO toDTO(Modalidade modalidade) {
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
        modalidadeDTO.setHorarios(modalidade.getHorario());
        List<TreinadorDTO> treinadorDTOS = TreinadorController.toDTOs(modalidade.getTreinadores());
        List<AtletaDTO> atletaDTOS = AtletaController.toDTOs(modalidade.getAtletas());
        modalidadeDTO.setEscaloes(modalidade.getEscaloes());
        modalidadeDTO.setTreinadores(treinadorDTOS);
        modalidadeDTO.setAtletas(atletaDTOS);
        return modalidadeDTO;
    }

    List<ModalidadeDTO> toDTOs(List<Modalidade> modalidades) {
        return modalidades.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/") //"/api/modalidades/"
    public List<ModalidadeDTO> all() {
        try {
            return toDTOs(modalidadeBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_MODALIDADES", e);
        }
    }

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

    @POST
    @Path("/") //"/api/modalidades/"
    public Response createNewModalidade(ModalidadeDTO modalidadeDTO) throws MyEntityExistsException, MyConstraintViolationException {
        modalidadeBean.create(modalidadeDTO.getId(),modalidadeDTO.getNome());
        Modalidade modalidade = modalidadeBean.findModalidade(modalidadeDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(modalidade)).build();
    }


    @PUT
    @Path("{id}")
    public Response updateModalide(@PathParam("id") int id, ModalidadeDTO modalidadeDTO) throws MyEntityNotFoundException {
        modalidadeBean.update(id,
                modalidadeDTO.getNome());
        Modalidade modalidade = modalidadeBean.findModalidade(id);
        return Response.status(Response.Status.OK).entity(toDTO(modalidade)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteModalidade(@PathParam("id") int id,ModalidadeDTO modalidadeDTO) throws MyEntityNotFoundException{
        modalidadeBean.remove(id);
        return Response.status(Response.Status.OK).build();
    }
}
