package pt.uc.dei.paj.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pt.uc.dei.paj.dto.UserDto;

@Path("/users")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public interface UserServicesInterface {

	/*
	 *     >>>    U S E R    S E R V I C E S    <<<
	 */

	@POST
	public Response register(UserDto userDto);
	
	@POST
	@Path("/admin")
	public Response registerAdmin(UserDto userDto);
	
	@POST
	@Path("/login")
	public Response login(
			@HeaderParam("email") String email, 
			@HeaderParam("password") String password);
	
	
	@POST
	@Path("/logout")
	public Response logout(
			@HeaderParam("email") String email, 
			@HeaderParam("token") String token);
	
	
	@PUT
	@Path("/{userId}")
	public Response changeRole(
			@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("userId") int userId,
			@QueryParam("admin") boolean isAdmin);
	
	@GET
	@Path("/{id}")
	public Response findById(@HeaderParam("email") String email, @HeaderParam("token") String token, @PathParam("id") int id);
	
	@GET
	public Response findUser(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@DefaultValue("") @QueryParam("isAdmin") String isAdmin);

}