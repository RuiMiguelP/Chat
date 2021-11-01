package pt.uc.dei.paj.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import pt.uc.dei.paj.dto.StatsDto;
import pt.uc.dei.paj.entity.Channel;
import pt.uc.dei.paj.entity.User;

@Stateless
public class ChannelDao extends AbstractDao<Channel>{
	private static final long serialVersionUID = 1L;

	public ChannelDao() {
		super(Channel.class);
	}
	
	/*
	 * Return channel by Id
	 */
	public Channel findById(int id) {
		try {
			TypedQuery<Channel> channels = em.createNamedQuery("Channel.findById", Channel.class);
			channels.setParameter("id", id);
			return channels.getSingleResult();
		} catch (Exception e) {
			System.out.println("ChannelDao - findById: " + e);
			return null;
		}
	}
	
	
	/*
	 * Retrieve all channels
	 */
	public ArrayList<Channel> findAllChannels() {
		try {
			TypedQuery<Channel> channels = em.createNamedQuery("Channel.findAllChannels", Channel.class);
			return (ArrayList<Channel>) channels.getResultList();
		} catch (Exception e) {
			System.out.println("ChannelDao - findAll: " + e);
			return null;
		}
	}
	
	/*
	 * Retrieve all conversation
	 */
	public ArrayList<Channel> findAllConversations() {
		try {
			TypedQuery<Channel> channels = em.createNamedQuery("Channel.findAllConversations", Channel.class);
			return (ArrayList<Channel>) channels.getResultList();
		} catch (Exception e) {
			System.out.println("ChannelDao - findAll: " + e);
			return null;
		}
	}
	
	/*
	 * Return active channels by workspaceId
	 */
	public ArrayList<Channel> findChannelsByWorkspace(int workspaceId) {
		try {
			TypedQuery<Channel> channels = em.createNamedQuery("Channel.findChannelsByWorkspace", Channel.class);
			channels.setParameter("workspaceId", workspaceId);
			channels.setParameter("isActive", true);
			channels.setParameter("isDirect", false);
			

			return (ArrayList<Channel>) channels.getResultList();
		} catch (Exception e) {
			System.out.println("ChannelDao - findChannelsByWorkspace: " + e);
			return null;
		}
	}
	
	public Channel findChannelByWorkspaceAndUsers(int workspaceId, User user, User otherUser) {
		try {
			TypedQuery<Channel> channel = em.createNamedQuery("Channel.findConversationByWorkspaceAndUsers", Channel.class);
			channel.setParameter("workspaceId", workspaceId);
			channel.setParameter("user", user);
			channel.setParameter("otherUser", otherUser);
			channel.setParameter("isDirect", true);

			return (Channel) channel.getSingleResult();
		} catch (Exception e) {
			System.out.println("ChannelDao - findConversationByWorkspaceAndUsers: " + e);
			return null;
		}
	}
	
	
	
	/*
	 * Return number of channels per workspace
	 */
	public ArrayList<StatsDto> countTotalWorkspaces() {
		try {
			ArrayList<StatsDto> channelsPerWorkspace = new ArrayList<>();
			Query q = em.createNativeQuery("SELECT COUNT(cha.id), cha.workspace_id, wrk.title FROM tp6.channel cha INNER JOIN tp6.workspace wrk ON cha.workspace_id=wrk.id AND cha.isDirect = false GROUP BY cha.workspace_id;");
			List<Object[]> listObject = q.getResultList();
			
			if (!listObject.isEmpty()) {
				for (Object[] obj: listObject) {
					StatsDto statDto = new StatsDto();
					statDto.setValue((String.valueOf(obj[0])));
					statDto.setType_id((String.valueOf(obj[1])));
					statDto.setName((String.valueOf(obj[2])));
					channelsPerWorkspace.add(statDto);
				}
				
				return channelsPerWorkspace;
			}
	        
	        return null; 
		} catch (Exception e) {
			System.out.println("WorkspaceDao - countTotalWorkspaces: " + e);
			return null;
		}
	}
	
}
