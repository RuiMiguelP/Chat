package pt.uc.dei.paj.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.security.auth.login.CredentialException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pt.uc.dei.paj.dto.HttpMessageDto;
import pt.uc.dei.paj.dto.WorkspaceDto;
import pt.uc.dei.paj.util.Constants;

@Path("/workspaces")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public class WebWorkspaceServices extends WebServices {

	@Inject
	HttpWorkspaceProxyProvider proxyProvider;

	/*
	 * >>> W O R K S P A C E S E R V I C E S <<<
	 */

	/**
	 * Create a new workspace
	 * 
	 */
	@POST
	public Response createWorkspace(@FormParam("title") String title, @HeaderParam("email") String email,
			@HeaderParam("token") String token) {

		Response response = proxyProvider.getProxy().createWorkspace(title, email, token);
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

	/**
	 * Subscribe/Unsubscribe a workspace
	 * 
	 */
	@PUT
	@Path("/{workspaceId}")
	public Response subscriberWorkspace(@PathParam("workspaceId") int workspaceId, @HeaderParam("email") String email,
			@HeaderParam("token") String token, @DefaultValue("") @QueryParam("userId") String userId) {

		Response response = proxyProvider.getProxy().subscriberWorkspace(workspaceId, email, token, userId);
		int status = response.getStatus();

		if (status == Response.Status.OK.getStatusCode()) {
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

	/**
	 * Cancel a workspace
	 * 
	 */
	@DELETE
	@Path("/{workspaceId}")
	public Response closeWorkspace(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("workspaceId") int workspaceId) {

		Response response = proxyProvider.getProxy().closeWorkspace(email, token, workspaceId);
		int status = response.getStatus();

		if (status == Response.Status.OK.getStatusCode()) {
			return Response.ok(response.getEntity()).build();
		} else {
			response.close();
		}

		switch (status) {
		case 400:
			return Response.status(status).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
		case 401:
			return Response.status(status).entity(new HttpMessageDto(Constants.UNAUTHORIZED_PERMISSION)).build();
		}
		return defaultErrors(status);
	}

	/*
	 * >>> C H A N N E L S E R V I C E S <<<
	 */

	/**
	 * Create a new channel associate to workspace or conversation
	 * 
	 */
	@POST
	@Path("/{workspaceId}/channels")
	public Response createChannel(@PathParam("workspaceId") int workspaceId,
			@DefaultValue("") @FormParam("title") String title, @DefaultValue("") @FormParam("userId") String userId,
			@HeaderParam("email") String email, @HeaderParam("token") String token) {

		Response response = proxyProvider.getProxy().createChannel(workspaceId, title, userId, email, token);
		int status = response.getStatus();

		if (status == Response.Status.CREATED.getStatusCode()) {
			return Response.ok(response.getEntity()).build();
		} else {
			response.close();
		}

		switch (status) {
		case 400:
			return Response.status(status).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
		case 401:
			return Response.status(status).entity(new HttpMessageDto(Constants.UNAUTHORIZED_PERMISSION)).build();
		}
		return defaultErrors(status);
	}

	/**
	 * Subscribe/Unsubscribe a channel associate with a workspace
	 * 
	 */
	@PUT
	@Path("/{workspaceId}/channels/{channelId}")
	public Response subscriberChannel(@PathParam("workspaceId") int workspaceId, @PathParam("channelId") int channelId,
			@HeaderParam("email") String email, @HeaderParam("token") String token,
			@DefaultValue("") @QueryParam("userId") String userId) {

		Response response = proxyProvider.getProxy().subscriberChannel(workspaceId, channelId, email, token, userId);
		int status = response.getStatus();

		if (status == Response.Status.OK.getStatusCode()) {
			return Response.ok(response.getEntity()).build();
		} else {
			response.close();
		}

		switch (status) {
		case 400:
			return Response.status(status).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
		case 401:
			return Response.status(status).entity(new HttpMessageDto(Constants.CONVERSATION_NOT_EDIT)).build();
		}
		return defaultErrors(status);
	}

	/**
	 * Cancel a channel
	 *
	 */
	@DELETE
	@Path("/{workspaceId}/channels/{channelId}")
	public Response closeChannel(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("workspaceId") int workspaceId, @PathParam("channelId") int channelId) {

		Response response = proxyProvider.getProxy().closeChannel(email, token, workspaceId, channelId);
		int status = response.getStatus();

		if (status == Response.Status.OK.getStatusCode()) {
			return Response.ok(response.getEntity()).build();
		} else {
			response.close();
		}

		switch (status) {
		case 400:
			return Response.status(status).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
		}
		return defaultErrors(status);
	}

	/*
	 * >>> GETTERS <<<
	 */

	/*
	 * Retorna todos os workspaces associados
	 */
	@GET
	public Response findWorkspacesByUser(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@DefaultValue("") @QueryParam("userId") int userId,
			@DefaultValue("") @QueryParam("isActive") String isActive) {

		Response response = proxyProvider.getProxy().findWorkspacesByUser(email, token, userId, isActive);
		int status = response.getStatus();

		if (status == Response.Status.OK.getStatusCode()) {
			return Response.ok(response.getEntity()).build();
		} else {
			response.close();
		}

		return defaultErrors(status);
	}

	/*
	 *
	 * Return active channels by workspaceId
	 */
	@GET
	@Path("/{workspaceId}/channels")
	public Response findChannelsByWorkspace(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("workspaceId") int workspaceId) {

		Response response = proxyProvider.getProxy().findChannelsByWorkspace(email, token, workspaceId);
		int status = response.getStatus();

		if (status == Response.Status.OK.getStatusCode()) {
			return Response.ok(response.getEntity()).build();
		} else {
			response.close();
		}

		return defaultErrors(status);
	}

	/*
	 *
	 * Return users by workspaceId
	 */
	@GET
	@Path("/{workspaceId}/users")
	public Response findUsersByWorkspace(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("workspaceId") int workspaceId) {

		Response response = proxyProvider.getProxy().findUsersByWorkspace(email, token, workspaceId);
		int status = response.getStatus();

		if (status == Response.Status.OK.getStatusCode()) {
			return Response.ok(response.getEntity()).build();
		} else {
			response.close();
		}

		return defaultErrors(status);
	}

	/*
	 *
	 * Find if conversation already exists
	 */
	@GET
	@Path("/{workspaceId}/conversation")
	public Response findConversationByUsers(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("workspaceId") int workspaceId, @QueryParam("otherUserId") int otherUserId) {

		Response response = proxyProvider.getProxy().findConversationByUsers(email, token, workspaceId, otherUserId);
		int status = response.getStatus();

		if (status == Response.Status.OK.getStatusCode()) {
			return Response.ok(response.getEntity()).build();
		} else {
			response.close();
		}

		return defaultErrors(status);
	}

	/*
	 * Return all workspaces
	 */
	@GET
	@Path("/stats")
	public Response findAllWorkspaces(@HeaderParam("email") String email, @HeaderParam("token") String token) {
		Response response = proxyProvider.getProxy().findAllWorkspaces(email, token);
		int status = response.getStatus();

		if (status == Response.Status.OK.getStatusCode()) {
			return Response.ok(response.getEntity()).build();
		} else {
			response.close();
		}

		return defaultErrors(status);
	}
}
