package exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyConstraintViolationExceptionMapper implements ExceptionMapper<MyConstraintViolationException> {
    @Override
    public Response toResponse(MyConstraintViolationException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();

    }
}
