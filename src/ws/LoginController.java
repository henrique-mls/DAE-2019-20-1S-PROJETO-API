package ws;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import dtos.AuthDTO;
import ejbs.UserBean;
import entities.User;
import jwt.Jwt;
import ejbs.JwtBean;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.logging.Logger;


@Path("/login")
public class LoginController {
    private static final Logger log =
            Logger.getLogger(LoginController.class.getName());
    @EJB
    JwtBean jwtBean;
    @EJB
    UserBean userBean;

    @POST
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(AuthDTO authDTO) {
        try {
            User user = userBean.authenticate(authDTO.getUsername(),  authDTO.getPassword());
            if (user != null) {
                if (user.getUsername() != null) {
                    log.info("Generating JWT for user " + user.getUsername());
                }
                String token = jwtBean.createJwt(user.getUsername(), new
                        String[]{user.getClass().getSimpleName()});
                return Response.ok(new Jwt(token)).build();
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("/claims")
    public Response demonstrateClaims(@HeaderParam("Authorization") String auth) {
        if (auth != null && auth.startsWith("Bearer ")) {
            try {
                JWT j = JWTParser.parse(auth.substring(7));
                return Response.ok(j.getJWTClaimsSet().getClaims()).build();
            //Note: nimbusds converts token expiration time to milliseconds
            } catch (ParseException e) {
                log.warning(e.toString());
                return Response.status(400).build();
            }
        }
        return Response.status(204).build(); //no jwt means no claims to extract
    }
}
