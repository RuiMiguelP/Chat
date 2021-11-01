package pt.uc.dei.paj.service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.security.auth.login.CredentialException;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pt.uc.dei.paj.dao.ChannelDao;
import pt.uc.dei.paj.dao.UserDao;
import pt.uc.dei.paj.dao.WorkspaceDao;
import pt.uc.dei.paj.dto.ChannelDto;
import pt.uc.dei.paj.dto.HttpMessageDto;
import pt.uc.dei.paj.dto.UserDto;
import pt.uc.dei.paj.dto.WorkspaceDto;
import pt.uc.dei.paj.entity.Channel;
import pt.uc.dei.paj.entity.User;
import pt.uc.dei.paj.entity.Workspace;
import pt.uc.dei.paj.util.Constants;
import pt.uc.dei.paj.util.EntityMapper;
import pt.uc.dei.paj.util.Validator;
import pt.uc.dei.paj.ws.NotificationBean;

@Path("/workspaces")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public class WorkspaceService {

	@Inject
	WorkspaceDao workspaceDao;

	@Inject
	UserDao userDao;

	@Inject
	ChannelDao channelDao;
	
	@Inject
	NotificationBean notificationBean;

	/**
	 * Create a new workspace
	 * 
	 */
	@POST
	public Response createWorkspace(@FormParam("title") String title, @HeaderParam("email") String email,
			@HeaderParam("token") String token) {

		System.out.println("**** Calling createWorkspace ****");

		try {
			User user = userDao.validateSession(email, token);

			if (!Validator.isNullOrEmpty(title)) {

				Workspace workspace = new Workspace();
				workspace.setTitle(title);
				workspace.setActive(true);
				workspace.setCreatedDate(LocalDateTime.now());
				workspace.setUser(user);

				if (user.isAdmin() == false) {
					workspace.setUsers(new HashSet<>(Arrays.asList(user)));
				}
				workspaceDao.persist(workspace);
				WorkspaceDto workspaceDto = workspace.toDto();

				return Response.status(Status.CREATED)
						.entity(new WorkspaceDto(workspaceDto.getId(), workspaceDto.getTitle(), workspaceDto.isActive(),
								workspaceDto.getUser(), workspaceDto.getUsers()))
						.build();
			}
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FIELDS)).build();
		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new HttpMessageDto(Constants.INTERNAL_SERVER_ERROR)).build();
		}
	}

	/**
	 * Subscribe/Unsubscribe a workspace
	 * 
	 */
	@PUT
	@Path("/{workspaceId}")
	public Response subscriberWorkspace(@PathParam("workspaceId") int workspaceId, @HeaderParam("email") String email,
			@HeaderParam("token") String token, @DefaultValue("") @QueryParam("userId") String userId) {

		try {
			User user = userDao.validateSession(email, token);
			Workspace workspace = workspaceDao.findById(workspaceId);

			if (workspace != null) {
				if (userId.isEmpty()) {

					System.out.println("**** Calling subscriberWorkspace - remove own user from workspace ****");

					workspace.removeUser(user);
					workspaceDao.merge(workspace);

					return Response.status(Status.OK).entity(new HttpMessageDto(Constants.UNSUBSCRIPTION_WORKSPACE))
							.build();
				} else {

					System.out.println("**** Calling subscriberWorkspace - add specific user from workspace ****");

					User userToBeAdd = userDao.findById(Integer.parseInt(userId));
					workspace.addUser(userToBeAdd);
					workspaceDao.merge(workspace);

					return Response.status(Status.OK).entity(new HttpMessageDto(Constants.SUBSCRIPTION_WORKSPACE))
							.build();
				}

			}
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FIELDS)).build();
		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new HttpMessageDto(Constants.INTERNAL_SERVER_ERROR)).build();
		}
	}

	/**
	 * Cancel a workspace
	 * 
	 */
	@DELETE
	@Path("/{workspaceId}")
	public Response closeWorkspace(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("workspaceId") int workspaceId) {

		try {

			System.out.println("**** Calling closeWorkspace ****");

			User user = userDao.validateSession(email, token);
			Workspace workspace = workspaceDao.findById(workspaceId);

			if (workspace != null) {
				if (workspace.getUser().getId() == user.getId() || user.isAdmin()) {
					workspace.setActive(false);
					workspaceDao.merge(workspace);

					return Response.status(Response.Status.OK).entity(new HttpMessageDto(Constants.WORKSPACE_CLOSED))
							.build();
				}
				return Response.status(Status.UNAUTHORIZED)
						.entity(new HttpMessageDto(Constants.UNAUTHORIZED_PERMISSION)).build();
			}
			return Response.status(Status.NOT_FOUND).entity(new HttpMessageDto(Constants.WORKSPACE_NOT_FOUND)).build();
		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
		}
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
			@DefaultValue("") @FormParam("title") String title, 
			@DefaultValue("") @FormParam("userId") String userId,
			@HeaderParam("email") String email, @HeaderParam("token") String token) {

		try {
			User user = userDao.validateSession(email, token);
			Workspace workspace = workspaceDao.findById(workspaceId);

			if (workspace != null) {
				if (workspace.isActive()) {
					if (userId.isEmpty() && !title.isEmpty()) {
						if (!Validator.isNullOrEmpty(title)) {

							System.out.println("**** Calling createChannel ****");

							Channel channel = new Channel();
							channel.setTitle(title);
							channel.setActive(true);
							channel.setDirect(false);
							channel.setCreatedDate(LocalDateTime.now());
							channel.setWorkspace(workspace);
							channel.setUser(user);

							if (user.isAdmin() == false) {
								channel.setUsers(new HashSet<>(Arrays.asList(user)));
							}
							channelDao.persist(channel);
							ChannelDto channelService = channel.toDto();
							
							notificationBean.createNotificationChangeChannel(channel, user, workspace, Constants.NEW_CHANNEL);

							return Response.status(Status.CREATED)
									.entity(new ChannelDto(channelService.getId(), channelService.getTitle(),
											channelService.isDirect(), channelService.isActive(),
											channelService.getUser(), channelService.getUsers()))
									.build();
						}
						return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FIELDS))
								.build();
					} else {
						if (!Validator.isNullOrEmpty(userId)) {

							
								User otherUser = userDao.findById(Integer.valueOf(userId));

								Channel channelResult = channelDao.findChannelByWorkspaceAndUsers(workspaceId, user,
										otherUser);

								if (channelResult != null) {
									return Response.status(Status.UNAUTHORIZED)
											.entity(new HttpMessageDto(Constants.CONVERSATION_ALREADY_EXISTS)).build();
								}

								System.out.println("**** Calling createConversation ****");

								Channel channel = new Channel();
								channel.setTitle(Constants.CONVERSATION);
								channel.setActive(true);
								channel.setDirect(true);
								channel.setCreatedDate(LocalDateTime.now());
								channel.setWorkspace(workspace);
								channel.setUser(user);
								channel.setUsers(new HashSet<>(Arrays.asList(user, otherUser)));

								channelDao.persist(channel);
								ChannelDto channelService = channel.toDto();

								return Response.status(Status.CREATED)
										.entity(new ChannelDto(channelService.getId(), channelService.getTitle(),
												channelService.isDirect(), channelService.isActive(),
												channelService.getUser(), channelService.getUsers()))
										.build();
							}
						return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FIELDS))
								.build();
					}

				}
				return Response.status(Status.UNAUTHORIZED)
						.entity(new HttpMessageDto(Constants.CHANNEL_WORKSPACE_CLOSED)).build();
			}
			return Response.status(Status.NOT_FOUND).entity(new HttpMessageDto(Constants.WORKSPACE_NOT_FOUND)).build();

		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new HttpMessageDto(Constants.INTERNAL_SERVER_ERROR)).build();
		}
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

		try {
			User user = userDao.validateSession(email, token);
			Workspace workspace = workspaceDao.findById(workspaceId);
			Channel channel = channelDao.findById(channelId);

			if (workspace != null) {
				if (channel != null) {
					if (channel.getIsDirect() == false) {
						if (userId.isEmpty()) {

							System.out.println("**** Calling subscriberChannel - remove  user from channel ****");

							channel.removeUser(user);
							channelDao.merge(channel);

							notificationBean.createNotificationChangeChannel(channel, user, workspace, Constants.CHANNEL_UNSUBSCRIPTION);
							
							return Response.status(Status.OK)
									.entity(new HttpMessageDto(Constants.UNSUBSCRIPTION_CHANNEL)).build();
						} else {

							System.out.println(
									"**** Calling subscriberChannel - add specific user from workspace to channel ****");

							User userToBeAdd = userDao.findById(Integer.parseInt(userId));
							channel.addUser(userToBeAdd);
							channelDao.merge(channel);
							
							notificationBean.createNotificationChangeChannel(channel, user, workspace, Constants.CHANNEL_SUBSCRIPTION);

							return Response.status(Status.OK).entity(new HttpMessageDto(Constants.SUBSCRIPTION_CHANNEL))
									.build();
						}
					}
					return Response.status(Status.UNAUTHORIZED)
							.entity(new HttpMessageDto(Constants.CONVERSATION_NOT_EDIT)).build();
				}
				return Response.status(Status.NOT_FOUND).entity(new HttpMessageDto(Constants.CHANNEL_NOT_FOUND))
						.build();
			}
			return Response.status(Status.NOT_FOUND).entity(new HttpMessageDto(Constants.WORKSPACE_NOT_FOUND)).build();
		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new HttpMessageDto(Constants.INTERNAL_SERVER_ERROR)).build();
		}
	}

	/**
	 * Cancel a channel
	 *
	 */
	@DELETE
	@Path("/{workspaceId}/channels/{channelId}")
	public Response closeChannel(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("workspaceId") int workspaceId, @PathParam("channelId") int channelId) {

		try {

			System.out.println("**** Calling closeWorkspace ****");

			User user = userDao.validateSession(email, token);
			Workspace workspace = workspaceDao.findById(workspaceId);
			Channel channel = channelDao.findById(channelId);

			if (workspace != null) {
				if (workspace.getUser().getId() == user.getId() || user.isAdmin()) {
					if (channel != null) {
						if (channel.isDirect() == false) {
							channel.setActive(false);
							channelDao.merge(channel);
							
							notificationBean.createNotificationChangeChannel(channel, user, workspace, Constants.CLOSED_CHANNEL);

							return Response.status(Response.Status.OK)
									.entity(new HttpMessageDto(Constants.CHANNEL_CLOSED)).build();
						}
						return Response.status(Status.UNAUTHORIZED)
								.entity(new HttpMessageDto(Constants.CONVERSATION_NOT_EDIT)).build();
					}
					return Response.status(Status.NOT_FOUND).entity(new HttpMessageDto(Constants.CHANNEL_NOT_FOUND))
							.build();
				}
				return Response.status(Status.UNAUTHORIZED)
						.entity(new HttpMessageDto(Constants.UNAUTHORIZED_PERMISSION)).build();
			}
			return Response.status(Status.NOT_FOUND).entity(new HttpMessageDto(Constants.WORKSPACE_NOT_FOUND)).build();
		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
		}
	}

	/*
	 * >>> GETTERS <<<
	 */

	/*
	 * Return all workspaces
	 */
	@GET
	@Path("/stats")
	public Response findAllWorkspaces(@HeaderParam("email") String email, @HeaderParam("token") String token) {
		try {

			User userValidation = userDao.validateSession(email, token);

			if (userValidation.isAdmin()) {

				System.out.println("**** Calling findAllWorkspaces  ****");

				ArrayList<Workspace> result = (ArrayList<Workspace>) workspaceDao.findAllWorkspaces();

				if (result.isEmpty()) {
					return Response.status(Response.Status.NOT_FOUND).entity(Constants.RECORDS_NOT_EXISTS).build();
				}

				ArrayList<WorkspaceDto> resultDto = EntityMapper.toListWorkspaceDto(result);
				GenericEntity<List<WorkspaceDto>> list = new GenericEntity<List<WorkspaceDto>>(resultDto) {
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
	 * Retorna todos os workspaces associados
	 */
	@GET
	public Response findWorkspacesByUser(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@DefaultValue("") @QueryParam("userId") int userId,
			@DefaultValue("") @QueryParam("isActive") String isActive) {
		try {

			User userValidation = userDao.validateSession(email, token);

			if (isActive.isEmpty() && !String.valueOf(userId).isEmpty()) {
				System.out.println("**** Calling findWorkspacesByUser without state ****");

				ArrayList<Workspace> result = (ArrayList<Workspace>) workspaceDao.findWorkspacesByUser(userId);

				if (result.isEmpty()) {
					return Response.status(Response.Status.NOT_FOUND).entity(Constants.RECORDS_NOT_EXISTS).build();
				}

				ArrayList<WorkspaceDto> resultDto = EntityMapper.toListWorkspaceDto(result);
				GenericEntity<List<WorkspaceDto>> list = new GenericEntity<List<WorkspaceDto>>(resultDto) {
				};

				return Response.status(Response.Status.OK).entity(list).build();

			} else if (!isActive.isEmpty() && !Validator.isNullOrEmpty(String.valueOf(userId))) {

				System.out.println("**** Calling findWorkspacesByUser with state ****");

				if (isActive.equalsIgnoreCase("true") || isActive.equalsIgnoreCase("false")) {

					User user = userDao.findById(userId);
					if (user != null) {
						ArrayList<Workspace> result = (ArrayList<Workspace>) workspaceDao
								.findWorkspacesByUserAndState(user, isActive);

						if (result.isEmpty()) {
							return Response.status(Response.Status.NOT_FOUND).entity(Constants.RECORDS_NOT_EXISTS)
									.build();
						}

						ArrayList<WorkspaceDto> resultDto = EntityMapper.toListWorkspaceDto(result);
						GenericEntity<List<WorkspaceDto>> list = new GenericEntity<List<WorkspaceDto>>(resultDto) {
						};

						return Response.status(Response.Status.OK).entity(list).build();
					}
					return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FORMAT))
							.build();
				} else if (Validator.isNullOrEmpty(isActive) && Validator.isNullOrEmpty(String.valueOf(userId))) {

					if (userValidation.isAdmin()) {

						System.out.println("**** Calling findAllWorkspaces  ****");

						ArrayList<Workspace> result = (ArrayList<Workspace>) workspaceDao.findAllWorkspaces();

						if (result.isEmpty()) {
							return Response.status(Response.Status.NOT_FOUND).entity(Constants.RECORDS_NOT_EXISTS)
									.build();
						}

						ArrayList<WorkspaceDto> resultDto = EntityMapper.toListWorkspaceDto(result);
						GenericEntity<List<WorkspaceDto>> list = new GenericEntity<List<WorkspaceDto>>(resultDto) {
						};

						return Response.status(Response.Status.OK).entity(list).build();
					}
					return Response.status(Status.UNAUTHORIZED)
							.entity(new HttpMessageDto(Constants.UNAUTHORIZED_PERMISSION)).build();
				}
			}
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
		}
	}

	/*
	 *
	 * Return active channels by workspaceId
	 */
	@GET
	@Path("/{workspaceId}/channels")
	public Response findChannelsByWorkspace(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("workspaceId") int workspaceId) {

		try {
			User user = userDao.validateSession(email, token);

			System.out.println("**** Calling findChannelsByWorkspace ****");

			ArrayList<Channel> result = (ArrayList<Channel>) channelDao.findChannelsByWorkspace(workspaceId);

			if (result.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND).entity(Constants.RECORDS_NOT_EXISTS).build();
			}

			ArrayList<ChannelDto> resultDto = EntityMapper.toListChannelDto(result);
			GenericEntity<List<ChannelDto>> list = new GenericEntity<List<ChannelDto>>(resultDto) {
			};

			return Response.status(Response.Status.OK).entity(list).build();

		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
		}
	}

	/*
	 *
	 * Return users by workspaceId
	 */
	@GET
	@Path("/{workspaceId}/users")
	public Response findUsersByWorkspace(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("workspaceId") int workspaceId) {

		try {
			User user = userDao.validateSession(email, token);

			System.out.println("**** Calling findUsersByWorkspace ****");

			ArrayList<Workspace> result = workspaceDao.findUsersByWorkspace(workspaceId);

			if (result == null) {
				return Response.status(Response.Status.NOT_FOUND).entity(Constants.RECORDS_NOT_EXISTS).build();
			}

			ArrayList<WorkspaceDto> resultDto = EntityMapper.toListWorkspaceDto(result);
			ArrayList<UserDto> resultUsers = resultDto.get(0).getUsers();
			GenericEntity<List<UserDto>> list = new GenericEntity<List<UserDto>>(resultUsers) {
			};

			return Response.status(Response.Status.OK).entity(list).build();

		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
		}
	}

	/*
	 *
	 * Find if conversation already exists
	 */
	@GET
	@Path("/{workspaceId}/conversation")
	public Response findConversationByUsers(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("workspaceId") int workspaceId, @QueryParam("otherUserId") int otherUserId) {

		try {
			User user = userDao.validateSession(email, token);
			Workspace workspace = workspaceDao.findById(workspaceId);

			if (workspace != null) {
				if (workspace.isActive()) {

					User otherUser = userDao.findById(Integer.valueOf(otherUserId));

					System.out.println("**** Calling findConversationByUsers ****");

					Channel channelResult = channelDao.findChannelByWorkspaceAndUsers(workspaceId, user, otherUser);

					if (channelResult == null) {
						return Response.status(Response.Status.NOT_FOUND).entity(Constants.RECORDS_NOT_EXISTS).build();
					}

					ChannelDto channelResultDto = channelResult.toDto();

					return Response.status(Status.OK).entity(channelResultDto).build();

				}
				return Response.status(Status.UNAUTHORIZED)
						.entity(new HttpMessageDto(Constants.CHANNEL_WORKSPACE_CLOSED)).build();
			}
			return Response.status(Status.NOT_FOUND).entity(new HttpMessageDto(Constants.WORKSPACE_NOT_FOUND)).build();
		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new HttpMessageDto(Constants.INTERNAL_SERVER_ERROR)).build();
		}
	}
}
