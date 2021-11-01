package pt.uc.dei.paj.service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.security.auth.login.CredentialException;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pt.uc.dei.paj.dao.UserDao;
import pt.uc.dei.paj.dto.HttpMessageDto;
import pt.uc.dei.paj.dto.UserDto;
import pt.uc.dei.paj.entity.User;
import pt.uc.dei.paj.util.Constants;
import pt.uc.dei.paj.util.EntityMapper;
import pt.uc.dei.paj.util.Generator;
import pt.uc.dei.paj.util.PasswordUtils;
import pt.uc.dei.paj.util.Validator;

@Path("/users")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class UserService {

	@Inject
	UserDao userDao;

	@POST
	public Response register(UserDto userDto) {
		try {

			System.out.println("**** User Service - Calling register ****");

			if (Validator.isValidUser(userDto)) {
				if (userDao.findByEmail(userDto.getEmail()) == null) {
					User user = new User();
					user.setName(userDto.getName());
					user.setEmail(userDto.getEmail());
					String[] infoPassword = PasswordUtils.generateSecurePassword(userDto.getPassword());
					user.setSaltPassword(infoPassword[0]);
					user.setPassword(infoPassword[1]);
					user.setCreatedDate(LocalDateTime.now());
					user.setActive(false);
					user.setAdmin(false);
					userDao.persist(user);
					return Response.status(Status.CREATED).entity(new HttpMessageDto(Constants.USER_CREATED)).build();
				}
				return Response.status(Status.CONFLICT).entity(new HttpMessageDto(Constants.EMAIL_IN_USE)).build();
			}
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FIELDS)).build();
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new HttpMessageDto(Constants.REGISTRATION_FAILED)).build();
		}
	}

	@POST
	@Path("/admin")
	public Response registerAdmin(UserDto userDto) {
		try {

			System.out.println("**** User Service - Calling registerAdmin ****");

			if (Validator.isValidUser(userDto)) {
				if (userDao.findByEmail(userDto.getEmail()) == null) {
					User user = new User();
					user.setName(userDto.getName());
					user.setEmail(userDto.getEmail());
					String[] infoPassword = PasswordUtils.generateSecurePassword(userDto.getPassword());
					user.setSaltPassword(infoPassword[0]);
					user.setPassword(infoPassword[1]);
					user.setCreatedDate(LocalDateTime.now());
					user.setActive(false);
					user.setAdmin(true);
					userDao.persist(user);
					return Response.status(Status.CREATED).entity(new HttpMessageDto(Constants.USER_CREATED)).build();
				}
				return Response.status(Status.CONFLICT).entity(new HttpMessageDto(Constants.EMAIL_IN_USE)).build();
			}
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FIELDS)).build();
		} catch (Exception e) {
			System.out.println("ERROR: " + e);
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new HttpMessageDto(Constants.REGISTRATION_FAILED)).build();
		}
	}

	@POST
	@Path("/login")
	public Response login(@HeaderParam("email") String email, @HeaderParam("password") String password) {
		try {

			System.out.println("**** User Service - Calling login ****");

			if (Validator.isValidLogin(email, password)) {
				User user = userDao.findByEmail(email);
				if (user != null) {
					if (Validator.verifyProvidedPassword(password, user.getPassword(), user.getSaltPassword())) {
						String token = Generator.generateNewToken();
						user.setToken(token);
						user.setActive(true);
						userDao.merge(user);
						return Response.ok(new UserDto(user.getId(), user.getName(), user.getEmail(), user.isActive() ,user.isAdmin(),
								user.getToken())).build();
					}
					return Response.status(Status.UNAUTHORIZED)
							.entity(new HttpMessageDto(Constants.LOGIN_UNAUTHORIZED_PASSWORD)).build();
				}
				return Response.status(Status.NOT_FOUND).entity(new HttpMessageDto(Constants.LOGIN_NOT_FOUND_USER))
						.build();
			}
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FIELDS)).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new HttpMessageDto(Constants.INTERNAL_SERVER_ERROR)).build();
		}
	}

	@POST
	@Path("/logout")
	public Response logout(@HeaderParam("email") String email, @HeaderParam("token") String token) {
		try {

			System.out.println("**** User Service - Calling logout ****");

			User user = userDao.validateSession(email, token);
			user.setActive(false);
			user.setToken(null);
			userDao.merge(user);
			return Response.status(Status.OK).entity(new HttpMessageDto(Constants.LOG_OUT_SUCCESS)).build();
		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) { // se falhar o merge
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
		}
	}

	@PUT
	@Path("/{userId}")
	public Response changeRole(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("userId") int userId, @QueryParam("admin") boolean isAdmin) {
		try {

			System.out.println("**** User Service - Calling changeRole ****");

			User user = userDao.validateSession(email, token);
			if (user.isAdmin()) {
				userDao.updateRole(isAdmin, userId);
				return Response.status(Status.OK).entity(new HttpMessageDto(Constants.UPDATE_ROLE_SUCCESS)).build();
			}
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto(Constants.UNAUTHORIZED_PERMISSION))
					.build();
		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
		}
	}

	@GET
	@Path("/{id}")
	public Response findById(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@PathParam("id") int id) {

		try {
			System.out.println("**** User Service -  Calling findById ****");

			User user = userDao.validateSession(email, token);

			if (user.getId() == id || user.isAdmin()) {
				User result = userDao.findById(id);
				if (result == null) {
					return Response.status(Response.Status.NOT_FOUND).entity(Constants.RECORDS_NOT_EXISTS).build();
				}
				UserDto userDto = result.toDto();
				return Response.status(Response.Status.OK).entity(userDto).build();
			}
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto(Constants.UNAUTHORIZED_PERMISSION))
					.build();
		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
		}
	}

	@GET
	public Response findUser(@HeaderParam("email") String email, @HeaderParam("token") String token,
			@DefaultValue("") @QueryParam("isAdmin") String isAdmin) {

		try {
			User user = userDao.validateSession(email, token);

			ArrayList<User> result = new ArrayList<>();

			if (Validator.isNullOrEmpty(isAdmin)) {

				System.out.println("****User Service - Calling findAllUser ****");
				result = (ArrayList<User>) userDao.findAll();
			} else {

				System.out.println("****User Service - Calling findAllUserAdmin ****");

				result = (ArrayList<User>) userDao.findAllByAdmin(Boolean.getBoolean(isAdmin));
			}

			if (result.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND).entity(Constants.RECORDS_NOT_EXISTS).build();
			}
			ArrayList<UserDto> resultDto = EntityMapper.toUserDtoList(result);
			GenericEntity<List<UserDto>> list = new GenericEntity<List<UserDto>>(resultDto) {
			};

			return Response.status(Response.Status.OK).entity(list).build();
		} catch (CredentialException e) {
			return Response.status(Status.UNAUTHORIZED).entity(new HttpMessageDto("Error: " + e.getMessage())).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(new HttpMessageDto(Constants.INVALID_FORMAT)).build();
		}
	}
}
