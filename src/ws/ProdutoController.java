package ws;


import dtos.PagamentoDTO;
import dtos.ProdutoDTO;
import dtos.TreinadorDTO;
import ejbs.ProdutoBean;
import entities.Produto;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@DeclareRoles("Administrador")
@Path("/produtos") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”

public class ProdutoController {

    @EJB
    ProdutoBean produtoBean;

    ProdutoDTO toDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getTipo(),
                produto.getDescricao(),
                produto.getValorBase()
        );
    }
    ProdutoDTO toDTOWithPagamentos(Produto produto) {
        ProdutoDTO produtoDTO =  new ProdutoDTO(
                produto.getId(),
                produto.getTipo(),
                produto.getDescricao(),
                produto.getValorBase()
        );

        List<PagamentoDTO> pagamentoDTOS = PagamentoController.toDTOs(produto.getPagamentos());
        produtoDTO.setPagamentos(pagamentoDTOS);

        return  produtoDTO;
    }

    List<ProdutoDTO> toDTOs(List<Produto> produtos) {
        return produtos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @RolesAllowed("Administrador")
    @GET
    @Path("/") //"/api/produtos/"
    public List<ProdutoDTO> all() {
        try {
            return toDTOs(produtoBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_PRODUTOS", e);
        }
    }

    @RolesAllowed("Administrador")
    @GET
    @Path("{id}")
    public Response getProdutosDetails(@PathParam("id") int id){
        try{
            Produto produto = produtoBean.findProduto(id);
            if(produto !=null){
                return Response.status(Response.Status.OK).entity(toDTOWithPagamentos(produto)).build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e){
            throw new EJBException("ERROR_GET_PRODUTO_DETAILS", e);
        }
    }

    @RolesAllowed("Administrador")
    @POST
    @Path("/") //"/api/produtos/"
    public Response createNewProduto(ProdutoDTO produtoDTO) throws MyEntityExistsException, MyConstraintViolationException {
        produtoBean.create(produtoDTO.getId(),produtoDTO.getTipo(),produtoDTO.getDescricao(),produtoDTO.getValorBase());
        Produto produto = produtoBean.findProduto(produtoDTO.getId());
        return Response.status(Response.Status.CREATED).entity(toDTO(produto)).build();
    }


    @RolesAllowed("Administrador")
    @PUT
    @Path("{id}")
    public Response updateProduto(@PathParam("id") int id, ProdutoDTO produtoDTO) throws MyEntityNotFoundException {
        produtoBean.update(id,
                produtoDTO.getTipo(),
                produtoDTO.getDescricao(),
                produtoDTO.getValorBase());
        Produto produto = produtoBean.findProduto(id);
        return Response.status(Response.Status.OK).entity(toDTO(produto)).build();
    }

    @RolesAllowed("Administrador")
    @DELETE
    @Path("{id}")
    public Response deleteProduto(@PathParam("id") int id,ProdutoDTO produtoDTO) throws MyEntityNotFoundException{
        produtoBean.remove(id);
        return Response.status(Response.Status.OK).build();
    }
}
