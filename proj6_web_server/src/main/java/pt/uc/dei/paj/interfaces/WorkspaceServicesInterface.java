package pt.uc.dei.paj.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
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



@Path("/workspaces")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public interface WorkspaceServicesInterface {

	/*
	 *     >>>    W O R  K S P A C E    S E R V I C E S    <<<
	 */

	/**
	 * Create a new workspace
	 * 
	 */
	@POST
	public Response createWorkspace(
			@FormParam("title") String title, 
			@HeaderParam("email") String email,
			@HeaderParam("token") String token);

	/**
	 * Subscribe/Unsubscribe a workspace
	 * 
	 */
	@PUT
	@Path("/{workspaceId}")
	public Response subscriberWorkspace(
			@PathParam("workspaceId") int workspaceId, 
			@HeaderParam("email") String email,
			@HeaderParam("token") String token, 
			@DefaultValue("") @QueryParam("userId") String userId);

	/**
	 * Cancel a workspace
	 * 
	 */
	@DELETE
	@Path("/{workspaceId}")
	public Response closeWorkspace(
			@HeaderParam("email") String email, 
			@HeaderParam("token") String token,
			@PathParam("workspaceId") int workspaceId);

	/*
	 * >>> C H A N N E L S E R V I C E S <<<
	 */

	/**
	 * Create a new channel associate to workspace or conversation
	 * 
	 */
	@POST
	@Path("/{workspaceId}/channels")
	public Response createChannel(
			@PathParam("workspaceId") int workspaceId, 
			@DefaultValue("") @FormParam("title") String title,
			@DefaultValue("") @FormParam("userId") String userId,
			@HeaderParam("email") String email, @HeaderParam("token") String token);

	/**
	 * Subscribe/Unsubscribe a channel associate with a workspace
	 * 
	 */
	@PUT
	@Path("/{workspaceId}/channels/{channelId}")
	public Response subscriberChannel(
			@PathParam("workspaceId") int workspaceId, 
			@PathParam("channelId") int channelId,
			@HeaderParam("email") String email, 
			@HeaderParam("token") String token,
			@DefaultValue("") @QueryParam("userId") String userId);
	
	/**
	 * Cancel a channel
	 *
	 */
	@DELETE
	@Path("/{workspaceId}/channels/{channelId}")
	public Response closeChannel(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("workspaceId") int workspaceId, @PathParam("channelId") int channelId);
	
	/*
	 * >>> GETTERS <<<
	 */
	
	/*
	 * Retorna todos os workspaces associados
	 */
	@GET
	public Response findWorkspacesByUser(
			@HeaderParam("email") String email, @HeaderParam("token") String token,
			@DefaultValue("") @QueryParam("userId") int userId,
			@DefaultValue("") @QueryParam("isActive") String isActive);
	/*
	 *
	 * Return active channels by workspaceId
	 */
	@GET
	@Path("/{workspaceId}/channels")
	public Response findChannelsByWorkspace(
			@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("workspaceId") int workspaceId);
	
	
	/*
	 *
	 * Return users by workspaceId
	 */
	@GET
	@Path("/{workspaceId}/users")
	public Response findUsersByWorkspace(
			@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("workspaceId") int workspaceId);
	
	/*
	 *
	 * Find if conversation already exists
	 */
	@GET
	@Path("/{workspaceId}/conversation")
	public Response findConversationByUsers(
			@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("workspaceId") int workspaceId,
			@QueryParam("otherUserId") int otherUserId);
	
	
	/*
	 *
	 * Retrieve all workspaces
	 */
	@GET
	@Path("/stats")
	public Response findAllWorkspaces(
			@HeaderParam("email") String email, @HeaderParam("token") String token);
	
}
