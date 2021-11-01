package pt.uc.dei.paj.util;

import java.util.ArrayList;

import pt.uc.dei.paj.dto.StatsDto;

public final class Constants {

	private Constants(){}

	public static final String EMAIL_REGEX = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?$";
	public static final String USERNAME_REGEX = "^\\S+$"; // um ou mais caracteres sem espaços
	public static final String PASSWORD_REGEX = "^(?=\\S+$).{6,}$"; // min 6 caracteres, sem espaços
	public static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	public static final int ITERATIONS = 10000;
	public static final int KEY_LENGTH = 256;
	
	// Encrypted and Base64 encoded password read from database
	public static final String SECUREPASSWORD = "HhaNvzTsVYwS/x/zbYXlLOE3ETMXQgllqrDaJY9PD/U=";
    
    // Salt value stored in database 
	public static final String SALT = "EqdmPh53c9x33EygXpTpcoJvc4VXLK";
	
	//Zone date
	public static final String ZONE_DATE_TIME = "Europe/London";
	
	public static final String RECORDS_NOT_EXISTS = "Record doesn't exists";

	/***************************************************************************************************
     *                                    Http Messages 	                                               *
     ***************************************************************************************************/
	public static final String INVALID_FIELDS = "ERROR: invalid fields.";
	public static final String EMAIL_IN_USE = "ERROR: email already in use.";
	public static final String REGISTRATION_FAILED = "ERROR: registration failed.";
	public static final String LOGIN_UNAUTHORIZED_PASSWORD = "LOGIN UNAUTHORIZED: invalid password.";
	public static final String LOGIN_NOT_FOUND_USER = "LOGIN NOT_FOUND: user doesn't exist.";
	public static final String WORKSPACE_NOT_FOUND = "ERROR: workspace doesn't exist.";
	public static final String CHANNEL_NOT_FOUND = "ERROR: channel doesn't exist.";

	public static final String INTERNAL_SERVER_ERROR = "Possible internal error.";
	public static final String LOG_OUT_SUCCESS = "Log out with success.";
	public static final String INVALID_FORMAT = "Invalid format";
	public static final String UPDATE_ROLE_SUCCESS = "Update role with success.";
	public static final String UNAUTHORIZED_PERMISSION = "UNAUTHORIZED: You don't have permission to do this operation.";
	public static final String USER_CREATED = "User created.";
	
	public static final String WORKSPACE_CREATED = "Workspace created.";
	public static final String WORKSPACE_CLOSED = "Workspace closed.";
	
	public static final String CHANNEL_CREATED = "Channel created.";
	public static final String CHANNEL_CLOSED = "Channel closed.";
	
	public static final String CHANNEL = "channel";
	public static final String NEW_CHANNEL = "new channel";
	public static final String CHANNEL_SUBSCRIPTION = "subscription channel";
	public static final String CHANNEL_UNSUBSCRIPTION = "unsubscription channel";
	public static final String CLOSED_CHANNEL =  "closed channel";
	public static final String CHANNEL_ACTION = "channel action";
	
	public static final String CONVERSATION = "conversation";
	
	
	public static final String CONVERSATION_CREATED = "Conversation created.";
	public static final String CONVERSATION_NOT_VALID = "A conversation between own user it's not possible.";

	public static final String CONVERSATION_NOT_EDIT = "Conversation cannot be changed.";
	public static final String CONVERSATION_ALREADY_EXISTS = "A conversation between this users already exist.";

	public static final String MESSAGE_CREATED = "Message send.";

	public static final String CHANNEL_WORKSPACE_CLOSED = "You cannot create a channel on a closed workspace.";
	public static final String MESSAGE_CHANNEL_CLOSED = "You cannot create a message on a closed channel.";
	
	public static final String SUBSCRIPTION_WORKSPACE = "Workspace subscription with success.";
	public static final String UNSUBSCRIPTION_WORKSPACE = "Workspace unsubscription with success.";

	public static final String SUBSCRIPTION_CHANNEL = "Channel subscription with success.";
	public static final String UNSUBSCRIPTION_CHANNEL = "Channel unsubscription with success.";
	
	
	/***************************************************************************************************
     *                                   Endpoint Paths	                                               *
     ***************************************************************************************************/	
	public static final String PATH = "http://localhost:8080/proj6_data_server/backrest";
	
	public static final String NUMBER_WORKSPACES = "number_workspaces";
	public static final String NUMBER_CHANNELS_PER_WORKSPACE = "number_channels_workspace";
	public static final String NUMBER_USERS = "number_users";
	public static final String NUMBER_MESSAGES_PER_WORKSPACE = "number_messages_workspace";
	public static final String NUMBER_MESSAGES_PER_CHANNEL = "number_messages_channel";
	public static final String NUMBER_MESSAGES_PER_CONVERSATION = "number_messages_conversation";
}
