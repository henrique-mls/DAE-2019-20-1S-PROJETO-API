package ws;

import dtos.PagamentoDTO;
import dtos.ProdutoDTO;
import ejbs.PagamentoBean;
import entities.Pagamento;
import entities.Produto;
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

@Path("/pagamentos") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class PagamentoController {

    @EJB
    PagamentoBean pagamentoBean;

    PagamentoDTO toDTO(Pagamento pagamento) {
        return new PagamentoDTO(
                pagamento.getId(),
                pagamento.getUtilizador().getUsername(),
                pagamento.getProduto().getId(),
                pagamento.getDataLancamento(),
                pagamento.getQuantidade(),
                pagamento.getPrecoFinal(),
                pagamento.getEstado()/*,
                pagamento.getRecibo()*/
        );
    }

    List<PagamentoDTO> toDTOs(List<Pagamento> pagamentos) {
        return pagamentos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/") //"/api/produtos/"
    public List<PagamentoDTO> all() {
        try {
            return toDTOs(pagamentoBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_PRODUTOS", e);
        }
    }

    @GET
    @Path("{id}")
    public Response getPagamentoDetails(@PathParam("id") int id){
        try{
            Pagamento pagamento = pagamentoBean.findPagamento(id);
            if(pagamento !=null){
                return Response.status(Response.Status.OK).entity(toDTO(pagamento)).build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e){
            throw new EJBException("ERROR_GET_PAGAMENTO_DETAILS", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewPagamento(PagamentoDTO pagamentoDTO) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        pagamentoBean.create(pagamentoDTO.getId(),pagamentoDTO.getUsername(),pagamentoDTO.getProdutoID(),pagamentoDTO.getDataLancamento(),
                pagamentoDTO.getQuantidade(),pagamentoDTO.getPrecoFinal(),pagamentoDTO.getEstado()/*,pagamentoDTO.getRecibo()*/);
        Pagamento pagamento = pagamentoBean.findPagamento(pagamentoDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(pagamento)).build();
    }

    @PUT
    @Path("{id}")
    public Response updatePagamento(@PathParam("id") int id, PagamentoDTO pagamentoDTO) throws MyEntityNotFoundException {
        pagamentoBean.update(id,
                pagamentoDTO.getUsername(),
                pagamentoDTO.getProdutoID(),
                pagamentoDTO.getDataLancamento(),
                pagamentoDTO.getQuantidade(),
                pagamentoDTO.getPrecoFinal(),
                pagamentoDTO.getEstado());
        Pagamento pagamento = pagamentoBean.findPagamento(id);
        return Response.status(Response.Status.OK).entity(toDTO(pagamento)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deletePagamento(@PathParam("id") int id) throws MyEntityNotFoundException{
        pagamentoBean.remove(id);
        return Response.status(Response.Status.OK).build();
    }
}
