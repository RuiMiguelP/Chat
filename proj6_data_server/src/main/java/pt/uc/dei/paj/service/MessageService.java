package pt.uc.dei.paj.service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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

import pt.uc.dei.paj.dao.ChannelDao;
import pt.uc.dei.paj.dao.MessageDao;
import pt.uc.dei.paj.dao.UserDao;
import pt.uc.dei.paj.dto.ChannelDto;
import pt.uc.dei.paj.dto.HttpMessageDto;
import pt.uc.dei.paj.dto.MessageDto;
import pt.uc.dei.paj.dto.UserDto;
import pt.uc.dei.paj.dto.WorkspaceDto;
import pt.uc.dei.paj.entity.Channel;
import pt.uc.dei.paj.entity.Message;
import pt.uc.dei.paj.entity.User;
import pt.uc.dei.paj.entity.Workspace;
import pt.uc.dei.paj.util.Constants;
import pt.uc.dei.paj.util.EntityMapper;
import pt.uc.dei.paj.util.Validator;
import pt.uc.dei.paj.ws.NotificationBean;

@Path("/channels")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public class MessageService {

	@Inject
	UserDao userDao;

	@Inject
	ChannelDao channelDao;
	
	@Inject
	MessageDao messageDao;
	
	@Inject
	NotificationBean notificationBean;

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
			@HeaderParam("token") String token) {
		try {
			boolean userCredential = false;
			User user = userDao.validateSession(email, token);
			Channel channel = channelDao.findById(channelId);
			
			if (channel != null) {
				if (channel.isActive()) {
						if (!Validator.isNullOrEmpty(bodyMessage)) {
								
							userCredential = EntityMapper.verifyUserOnChannel(channel.getUsers(), user);
							
							if (userCredential) {
								System.out.println("**** Calling createMessage ****");

								Message message = new Message();
								message.setBodyMessage(bodyMessage);
								message.setCreatedDate(LocalDateTime.now());
								message.setChannel(channel);
								message.setUser(user);
								message.setWorkspace(channel.getWorkspace());

								messageDao.persist(message);
								
								MessageDto messageDto = message.toDto();
								
								notificationBean.createNotification(channel, user, channel.getWorkspace(), bodyMessage);
								
								return Response.status(Status.CREATED).entity(new MessageDto(messageDto.getId(), messageDto.getCreatedDate(), 
										messageDto.getBodyMessage(),  messageDto.getUserDto())).build();
							}
						return Response.status(Status.UNAUTHORIZED)
								.entity(new HttpMessageDto(Constants.UNAUTHORIZED_PERMISSION)).build();
						}
						return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FIELDS)).build();
				}
				return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto(Constants.MESSAGE_CHANNEL_CLOSED)).build();
			}
			return Response.status(Status.NOT_FOUND).entity(new HttpMessageDto(Constants.CHANNEL_NOT_FOUND)).build();

		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new HttpMessageDto(Constants.INTERNAL_SERVER_ERROR))
					.build();
		}
	}
	
	/*
	 * >>> GETTERS <<<
	 */
	
	/*
	 * Return all channels
	 */
	@GET
	public Response findAllChannels(@HeaderParam("email") String email, @HeaderParam("token") String token) {
		try {

			User userValidation = userDao.validateSession(email, token);

			if (userValidation.isAdmin()) {

				System.out.println("**** Calling findAllChannels  ****");

				ArrayList<Channel> result = (ArrayList<Channel>) channelDao.findAllChannels();

				if (result.isEmpty()) {
					return Response.status(Response.Status.NOT_FOUND).entity(Constants.RECORDS_NOT_EXISTS).build();
				}

				ArrayList<ChannelDto> resultDto = EntityMapper.toListStatsChannelDto(result);
				GenericEntity<List<ChannelDto>> list = new GenericEntity<List<ChannelDto>>(resultDto) {
				};

				return Response.status(Response.Status.OK).entity(list).build();
			}
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto(Constants.UNAUTHORIZED_PERMISSION))
					.build();
		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
		}
	}	
	
	/*
	 * Return all messages from channel or conversation
	 */
	@GET
	@Path("/{channelId}/messages")
	public Response findMessagesByChannel(
			@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("channelId") int channelId) {
		try {
			User user = userDao.validateSession(email, token);
			
			System.out.println("**** Calling findMessagesByChannel ****");

			ArrayList<Message> result = (ArrayList<Message>) messageDao.findMessagesByChannel(channelId);
			
			if (result.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND).entity(Constants.RECORDS_NOT_EXISTS).build();
			}

			ArrayList<MessageDto> resultDto = EntityMapper.toListMessageDto(result);
			GenericEntity<List<MessageDto>> list = new GenericEntity<List<MessageDto>>(resultDto) {};

			return Response.status(Response.Status.OK).entity(list).build();	
		
		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
		}
	}
	
	
	
	/*
	 * Return all messages from channel or conversation
	 */
	@GET
	@Path("/{workspaceId}/conversation")
	public Response findConversationByWorkspaceAndUsers(
			@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("workspaceId") int workspaceId,
			@QueryParam("userId") int userId,
			@QueryParam("otherUserId") int otherUserId) {
		try {
			
			System.out.println("**** Calling findConversationByWorkspaceAndUsers ****");
			User userValid = userDao.validateSession(email, token);

			User user = userDao.findById(userId);
			User otherUser = userDao.findById(otherUserId);
			
			if (userValid.getEmail().equalsIgnoreCase(user.getEmail()) || userValid.getEmail().equalsIgnoreCase(otherUser.getEmail()) 
					|| userValid.isAdmin()) {
				if (user != null && otherUser != null) {
					
					Channel channelResult = channelDao.findChannelByWorkspaceAndUsers(workspaceId, user, otherUser);
					
					if (channelResult == null) {
						return Response.status(Response.Status.NOT_FOUND).entity(Constants.RECORDS_NOT_EXISTS).build();
					}

					ArrayList<Message> result = messageDao.findMessagesByChannel(channelResult.getId());
					
					if (result.isEmpty()) {
						return Response.status(Response.Status.NOT_FOUND).entity(Constants.RECORDS_NOT_EXISTS).build();
					}
					
					ArrayList<MessageDto> resultDto = EntityMapper.toListMessageDto(result);
					GenericEntity<List<MessageDto>> list = new GenericEntity<List<MessageDto>>(resultDto) {};

					return Response.status(Response.Status.OK).entity(list).build();	
				}
				return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
			}
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto(Constants.UNAUTHORIZED_PERMISSION)).build();
		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
		}
	}
}
