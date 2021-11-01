package pt.uc.dei.paj.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/channels")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public interface MessageServicesInterface {

	/*
	 * >>> M E S S A G E   S E R V I C E S <<<
	 */

	/**
	 * Create a new message associate to channel
	 * 
	 */
	@POST
	@Path("/{channelId}/messages")
	public Response createMessage(
			@PathParam("channelId") int channelId,
			@FormParam("bodyMessage") String bodyMessage,
			@HeaderParam("email") String email, 
			@HeaderParam("token") String token);
	
	/*
	 * >>> GETTERS <<<
	 */
	
	/*
	 * Return all messages from channel or conversation
	 */
	@GET
	@Path("/{channelId}/messages")
	public Response findMessagesByChannel(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("channelId") int channelId);
	
	
	
	/*
	 * Return all messages from channel or conversation
	 */
	@GET
	@Path("/{workspaceId}/conversation")
	public Response findConversationByWorkspaceAndUsers(
			@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("workspaceId") int workspaceId,
			@QueryParam("userId") int userId,
			@QueryParam("otherUserId") int otherUserId);
	
	
	/*
	 *
	 * Retrieve all channels
	 */
	@GET
	public Response findAllChannels(
			@HeaderParam("email") String email, @HeaderParam("token") String token);


}
