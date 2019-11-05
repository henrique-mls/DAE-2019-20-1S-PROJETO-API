package exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyIllegalArgumentExceptionMapper implements ExceptionMapper<MyIllegalArgumentException> {
    @Override
    public Response toResponse(MyIllegalArgumentException e) {
        return
                Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }

}
