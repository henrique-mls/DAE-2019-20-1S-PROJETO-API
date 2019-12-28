package ws;

import dtos.SocioDTO;
import dtos.TreinadorDTO;
import ejbs.SocioBean;
import entities.Socio;
import entities.Treinador;
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

@Path("/socios") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class SocioController {
    @EJB
    SocioBean socioBean;

    SocioDTO toDTO(Socio socio){
        return new SocioDTO(
                socio.getUsername(),
                socio.getName(),
                socio.getPassword(),
                socio.getEmail()
        );
    }

    List<SocioDTO> toDTOs(List<Socio> socios) {
        return socios.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/") //"/api/socios/"
    public List<SocioDTO> all() {
        try {
            return toDTOs(socioBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_SOCIOS", e);
        }
    }

    @GET
    @Path("{username}")
    public Response getSocioDetails(@PathParam("username") String username){
        try{
            Socio socio = socioBean.findSocio(username);
            if(socio !=null){
                return Response.status(Response.Status.OK).entity(toDTO(socio)).build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e){
            throw new EJBException("ERROR_GET_SOCIO_DETAILS", e);
        }
    }

    @POST
    @Path("/") //"/api/socios/"
    public Response createNewSocio(SocioDTO socioDTO) throws MyEntityExistsException, MyConstraintViolationException {
        socioBean.create(socioDTO.getUsername(),socioDTO.getName(),socioDTO.getPassword(),socioDTO.getEmail());
        Socio socio = socioBean.findSocio(socioDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(toDTO(socio)).build();
    }

    @PUT
    @Path("{username}")
    public Response updateSocio(@PathParam("username") String username, SocioDTO socioDTO) throws MyEntityNotFoundException {
        socioBean.update(username, socioDTO.getName(), socioDTO.getPassword(), socioDTO.getEmail());
        Socio socio = socioBean.findSocio(username);
        return Response.status(Response.Status.OK).entity(toDTO(socio)).build();
    }

    @DELETE
    @Path("{username}")
    public Response deleteSocio(@PathParam("username") String username, SocioDTO socioDTO) throws MyEntityNotFoundException{
        socioBean.remove(username);
        return Response.status(Response.Status.OK).build();
    }
}
