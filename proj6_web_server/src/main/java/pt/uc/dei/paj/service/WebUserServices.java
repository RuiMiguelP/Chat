package pt.uc.dei.paj.service;

import javax.inject.Inject;
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

import pt.uc.dei.paj.dto.HttpMessageDto;
import pt.uc.dei.paj.dto.UserDto;
import pt.uc.dei.paj.util.Constants;

@Path("/users")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public class WebUserServices extends WebServices {	
	
	@Inject
	HttpUserProxyProvider proxyProvider;
	

	/*
	 *     >>>    U S E R    S E R V I C E S    <<<
	 */
	
	@POST
	public Response register(UserDto userDto) {		
		Response response = proxyProvider.getProxy().register(userDto);
		int status = response.getStatus();
		
		if (status == Response.Status.CREATED.getStatusCode()) {
			return Response.ok(new HttpMessageDto(Constants.USER_CREATED)).build();
		} else {
			response.close();
		}
		
		response.close();
		return defaultErrors(status);
	}
	
	@POST
	@Path("/login")
	public Response login(
			@HeaderParam("email") String email, 
			@HeaderParam("password") String password) {
		
		Response response = proxyProvider.getProxy().login(email, password);
		int status = response.getStatus();
		
		if (status == Response.Status.OK.getStatusCode()) {
			return Response.ok(response.getEntity()).build();			
		} else {
			response.close();
		}
		
		switch (status) {
		case 401:
			return Response.status(status)
					.entity(new HttpMessageDto(Constants.LOGIN_UNAUTHORIZED_PASSWORD)).build();
		case 404:
			return Response.status(status)
					.entity(new HttpMessageDto(Constants.LOGIN_NOT_FOUND_USER)).build();
		}
		return defaultErrors(status);
	}
	
	@POST
	@Path("/logout")
	public Response logout(
			@HeaderParam("email") String email, 
			@HeaderParam("token") String token) {
	
		Response response = proxyProvider.getProxy().logout(email, token);
		int status = response.getStatus();
		
		if (status == Response.Status.OK.getStatusCode()) {
			return Response.ok(new HttpMessageDto(Constants.LOG_OUT_SUCCESS)).build();
		} else {
			response.close();
		}
		
		return defaultErrors(status);			
	}
	
	@PUT
	@Path("/{userId}")
	public Response changeRole(
			@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("userId") int userId,
			@QueryParam("admin") boolean isAdmin) {
		
		Response response = proxyProvider.getProxy().changeRole(email, token, userId, isAdmin);
		int status = response.getStatus();
		
		if (status == Response.Status.OK.getStatusCode()) {
			return Response.ok(response.getEntity()).build();			
		} else {
			response.close();
		}
		
		switch (status) {
		case 401:
			return Response.status(status)
					.entity(new HttpMessageDto(Constants.UNAUTHORIZED_PERMISSION)).build();
		}
		return defaultErrors(status);
	}
	
	@GET
	@Path("/{id}")
	public Response findById(@HeaderParam("email") String email, @HeaderParam("token") String token, @PathParam("id") int id) {

		Response response = proxyProvider.getProxy().findById(email, token, id);
		int status = response.getStatus();
		
		if (status == Response.Status.OK.getStatusCode()) {
			return Response.ok(response.getEntity()).build();			
		} else {
			response.close();
		}
		
		switch (status) {
		case 401:
			return Response.status(status)
					.entity(new HttpMessageDto(Constants.UNAUTHORIZED_PERMISSION)).build();
		case 404:
			return Response.status(status)
					.entity(new HttpMessageDto(Constants.RECORDS_NOT_EXISTS)).build();
		}
		return defaultErrors(status);
	}
	
	@GET
	public Response findUser(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@DefaultValue("") @QueryParam("isAdmin") String isAdmin) {
		
		Response response = proxyProvider.getProxy().findUser(email, token, isAdmin);
		int status = response.getStatus();
		
		if (status == Response.Status.OK.getStatusCode()) {
			return Response.ok(response.getEntity()).build();			
		} else {
			response.close();
		}
		
		switch (status) {
		case 401:
			return Response.status(status)
					.entity(new HttpMessageDto(Constants.UNAUTHORIZED_PERMISSION)).build();
		}
		return defaultErrors(status);
	}
}
