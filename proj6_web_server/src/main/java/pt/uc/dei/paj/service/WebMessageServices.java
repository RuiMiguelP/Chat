package pt.uc.dei.paj.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.security.auth.login.CredentialException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pt.uc.dei.paj.dto.ChannelDto;
import pt.uc.dei.paj.dto.HttpMessageDto;
import pt.uc.dei.paj.util.Constants;

@Path("/channels")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public class WebMessageServices extends WebServices {

	@Inject
	HttpMessageProxyProvider proxyProvider;

	/*
	 * >>> M E S S A G E S E R V I C E S <<<
	 */

	/**
	 * Create a new message associate to channel
	 * 
	 */
	@POST
	@Path("/{channelId}/messages")
	public Response createMessage(@PathParam("channelId") int channelId, @FormParam("bodyMessage") String bodyMessage,
			@HeaderParam("email") String email, @HeaderParam("token") String token) {

		Response response = proxyProvider.getProxy().createMessage(channelId, bodyMessage, email, token);
		int status = response.getStatus();

		if (status == Response.Status.CREATED.getStatusCode()) {
			return Response.ok(response.getEntity()).build();			
		} else {
			response.close();
		}

		switch (status) {
		case 400:
			return Response.status(status).entity(new HttpMessageDto(Constants.INVALID_FIELDS)).build();
		}
		return defaultErrors(status);
	}

	/*
	 * >>> GETTERS <<<
	 */
	
	
	/*
	 * Return all channels
	 */
	@GET
	public Response findAllChannels(@HeaderParam("email") String email, @HeaderParam("token") String token) {
		Response response = proxyProvider.getProxy().findAllChannels(email, token);
		int status = response.getStatus();

		if (status == Response.Status.OK.getStatusCode()) {
			return Response.ok(response.getEntity()).build();
		} else {
			response.close();
		}

		return defaultErrors(status);
	}


	/*
	 * Return all messages from channel or conversation
	 */
	@GET
	@Path("/{channelId}/messages")
	public Response findMessagesByChannel(
			@PathParam("channelId") int channelId,
			@HeaderParam("email") String email, @HeaderParam("token") String token) {
		Response response = proxyProvider.getProxy().findMessagesByChannel(email, token, channelId);
		int status = response.getStatus();

		if (status == Response.Status.OK.getStatusCode()) {
			return Response.ok(response.getEntity()).build();			
		} else {
			response.close();
		}

		return defaultErrors(status);
	}

	/*
	 * Return all messages from channel or conversation
	 */
	@GET
	@Path("/{workspaceId}/conversation")
	public Response findConversationByWorkspaceAndUsers(@PathParam("workspaceId") int workspaceId,
			@QueryParam("userId") int userId, @QueryParam("otherUserId") int otherUserId, 
			@HeaderParam("email") String email, @HeaderParam("token") String token) {

		Response response = proxyProvider.getProxy().findConversationByWorkspaceAndUsers(email, token, workspaceId, userId,
				otherUserId);
		int status = response.getStatus();

		if (status == Response.Status.OK.getStatusCode()) {
			return Response.ok(response.getEntity()).build();			
		} else {
			response.close();
		}

		return defaultErrors(status);
	}
}
