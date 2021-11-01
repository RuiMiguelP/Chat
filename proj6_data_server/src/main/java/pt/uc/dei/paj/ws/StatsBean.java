package pt.uc.dei.paj.ws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import pt.uc.dei.paj.dao.ChannelDao;
import pt.uc.dei.paj.dao.MessageDao;
import pt.uc.dei.paj.dao.UserDao;
import pt.uc.dei.paj.dao.WorkspaceDao;
import pt.uc.dei.paj.dto.StatsDto;
import pt.uc.dei.paj.dto.WorkspaceDto;
import pt.uc.dei.paj.entity.Workspace;
import pt.uc.dei.paj.util.Constants;
import pt.uc.dei.paj.util.Validator;

@ApplicationScoped
public class StatsBean {

	@Inject
	UserDao userDao;
	
	@Inject
	ChannelDao channelDao;
	
	@Inject
	MessageDao messageDao;
	
	@Inject
	WorkspaceDao workspaceDao;
	
	
	private static final Logger logger = Logger.getLogger("StatsBean");

	@PostConstruct
	public void init() {
		/* Initialize the EJB and create a timer */
		logger.log(Level.INFO, "Initializing EJB.");
	}

	// Calculate Stats
	public void calcStats() {
		HashMap<String, ArrayList<StatsDto>> mapDataStats = new HashMap<>();
		
		ArrayList<StatsDto> numberWorkspaces = generateNumberWorkspaces();
		ArrayList<StatsDto> numberChannelsPerWorkspace = generateChannelsPerWorkspace();
		ArrayList<StatsDto> numberUsers = generateNumberUsers();
		ArrayList<StatsDto> numberMessagesPerWorkspace = generateMessagesPerWorkspace();
		ArrayList<StatsDto> numberMessagesPerChannel = generateMessagesPerChannel();
		ArrayList<StatsDto> numberMessagesPerDirectMessage = generateMessagesPerDirectMessage();
		
		mapDataStats.put(Constants.NUMBER_WORKSPACES, numberWorkspaces);
		mapDataStats.put(Constants.NUMBER_CHANNELS_PER_WORKSPACE, numberChannelsPerWorkspace);
		mapDataStats.put(Constants.NUMBER_USERS, numberUsers);
		mapDataStats.put(Constants.NUMBER_MESSAGES_PER_WORKSPACE, numberMessagesPerWorkspace);
		mapDataStats.put(Constants.NUMBER_MESSAGES_PER_CHANNEL, numberMessagesPerChannel);
		mapDataStats.put(Constants.NUMBER_MESSAGES_PER_CONVERSATION, numberMessagesPerDirectMessage);
				 
		 StatsEndpoint.send(mapDataStats);
	}

	private ArrayList<StatsDto> generateNumberWorkspaces() {
		String value = workspaceDao.countTotalWorkspaces();
		
		if (Validator.isNullOrEmpty(value)) {
			value = "0";
		}
		
		return new ArrayList<>(Arrays.asList(new StatsDto(value)));
	}
	
	private ArrayList<StatsDto> generateChannelsPerWorkspace() {
		return channelDao.countTotalWorkspaces();	
	}
	
	private ArrayList<StatsDto> generateNumberUsers() {
		String value = userDao.countTotalUsers();
		
		if (Validator.isNullOrEmpty(value)) {
			value = "0";
		}
		
		return new ArrayList<>(Arrays.asList(new StatsDto(value)));
	}
	
	private ArrayList<StatsDto> generateMessagesPerWorkspace() {
		return messageDao.countTotalMessagesPerWorkspace();	
	}
	
	private ArrayList<StatsDto> generateMessagesPerChannel() {
		return messageDao.countTotalMessagesPerChannel();	
	}
	
	private ArrayList<StatsDto> generateMessagesPerDirectMessage() {
		return messageDao.countTotalMessagesPerDirectMessage();	
	}
}
