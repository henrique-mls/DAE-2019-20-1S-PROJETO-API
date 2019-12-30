package ws;

import dtos.ModalidadeDTO;
import dtos.TreinadorDTO;
import ejbs.ModalidadeBean;
import ejbs.TreinadorBean;
import entities.Modalidade;
import entities.Treinador;
import entities.User;
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

@Path("/treinadores") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class TreinadorController {
    @EJB
    TreinadorBean treinadorBean;

    public static TreinadorDTO toDTO(Treinador treinador) {
        return new TreinadorDTO(
                treinador.getUsername(),
                treinador.getName(),
                treinador.getPassword(),
                treinador.getEmail()
        );
    }

    public static List<TreinadorDTO> toDTOs(List<Treinador> treinadores) {
        return treinadores.stream().map(TreinadorController::toDTO).collect(Collectors.toList());
    }


    @GET
    @Path("/") //"/api/treinadores/"
    public List<TreinadorDTO> all() {
        try {
            return toDTOs(treinadorBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_TREINADORES", e);
        }
    }

    @GET
    @Path("{username}")
    public Response getTreinadorDetails(@PathParam("username") String username){
        try{
            Treinador treinador = treinadorBean.findTreinador(username);
            if(treinador !=null){
                return Response.status(Response.Status.OK).entity(toDTO(treinador)).build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e){
            throw new EJBException("ERROR_GET_TREINADOR_DETAILS", e);
        }
    }

    @POST
    @Path("/") //"/api/treinadores/"
    public Response createNewTreinador(TreinadorDTO treinadorDTO) throws MyEntityExistsException, MyConstraintViolationException {
        treinadorBean.create(treinadorDTO.getUsername(),treinadorDTO.getName(),treinadorDTO.getPassword(),treinadorDTO.getEmail());
        Treinador treinador = treinadorBean.findTreinador(treinadorDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(toDTO(treinador)).build();
    }

    @PUT
    @Path("{username}")
    public Response updateTreinador(@PathParam("username") String username, TreinadorDTO treinadorDTO) throws MyEntityNotFoundException {
        treinadorBean.update(username, treinadorDTO.getName(), treinadorDTO.getPassword(), treinadorDTO.getEmail());
        Treinador treinador = treinadorBean.findTreinador(username);
        return Response.status(Response.Status.OK).entity(toDTO(treinador)).build();
    }

    @DELETE
    @Path("{username}")
    public Response deleteTreinador(@PathParam("username") String username, TreinadorDTO treinadorDTO) throws MyEntityNotFoundException{
        treinadorBean.remove(username);
        return Response.status(Response.Status.OK).build();
    }
}
