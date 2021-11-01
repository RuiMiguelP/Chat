package pt.uc.dei.paj.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import pt.uc.dei.paj.dto.StatsDto;
import pt.uc.dei.paj.entity.Message;

@Stateless
public class MessageDao extends AbstractDao<Message> {
	private static final long serialVersionUID = 1L;

	public MessageDao() {
		super(Message.class);
	}
	
	public ArrayList<Message> findMessagesByChannel(int channelId) {
		try {
			TypedQuery<Message> messages = em.createNamedQuery("Message.findMessagesByChannel", Message.class);
			messages.setParameter("channelId", channelId);

			return (ArrayList<Message>) messages.getResultList();
		} catch (Exception e) {
			System.out.println("ChannelDao - findMessagesByChannel: " + e);
			return null;
		}
	}
	
	/*
	 * Return number of messages per channel
	 */
	public ArrayList<StatsDto> countTotalMessagesPerChannel() {
		try {
			ArrayList<StatsDto> messagesPerChannel = new ArrayList<>();
			Query q = em.createNativeQuery("SELECT COUNT(msg.id), channel_id, cha.title FROM tp6.message msg INNER JOIN tp6.channel cha ON msg.channel_id=cha.id AND cha.isDirect = false GROUP BY cha.id;");
			List<Object[]> listObject = q.getResultList();
			
			if (!listObject.isEmpty()) {
				for (Object[] obj: listObject) {
					StatsDto statDto = new StatsDto();
					statDto.setValue((String.valueOf(obj[0])));
					statDto.setType_id((String.valueOf(obj[1])));
					statDto.setName((String.valueOf(obj[2])));
					messagesPerChannel.add(statDto);
				}
				
				return messagesPerChannel;
			}
			
			return null; 
		} catch (Exception e) {
			System.out.println("MessageDao - countTotalMessagesPerChannel: " + e);
			return null;
		}
	}
	
	/*
	 * Return number of messages per direct message
	 */
	public ArrayList<StatsDto> countTotalMessagesPerDirectMessage() {
		try {			
			ArrayList<StatsDto> messagesPerDirectMessage = new ArrayList<>();
			Query q = em.createNativeQuery("SELECT COUNT(msg.id), channel_id, cha.title FROM tp6.message msg INNER JOIN tp6.channel cha ON msg.channel_id=cha.id AND cha.isDirect = true GROUP BY cha.id;");
			List<Object[]> listObject = q.getResultList();
						
			if (!listObject.isEmpty()) {
				for (Object[] obj: listObject) {
					StatsDto statDto = new StatsDto();
					statDto.setValue((String.valueOf(obj[0])));
					statDto.setType_id((String.valueOf(obj[1])));
					statDto.setName((String.valueOf(obj[2])));
					messagesPerDirectMessage.add(statDto);
				}
			}
				
			return messagesPerDirectMessage;
		} catch (Exception e) {
			System.out.println("MessageDao - countTotalMessagesPerDirectMessage: " + e);
			return null;
		}
	}
	
	/*
	 * Return number of messages per workspace
	 */
	public ArrayList<StatsDto> countTotalMessagesPerWorkspace() {
		try {
			ArrayList<StatsDto> messagesPerWorkspace = new ArrayList<>();
			Query q = em.createNativeQuery("SELECT COUNT(msg.id), msg.workspace_id, wrk.title FROM tp6.message msg INNER JOIN tp6.workspace wrk ON msg.workspace_id=wrk.id GROUP BY wrk.id;");
			List<Object[]> listObject = q.getResultList();

			if (!listObject.isEmpty()) {
				for (Object[] obj: listObject) {
					StatsDto statDto = new StatsDto();
					statDto.setValue((String.valueOf(obj[0])));
					statDto.setType_id((String.valueOf(obj[1])));
					statDto.setName((String.valueOf(obj[2])));
					messagesPerWorkspace.add(statDto);
				}
			}				
			return messagesPerWorkspace; 
		} catch (Exception e) {
			System.out.println("MessageDao - countTotalMessagesPerWorkspace: " + e);
			return null;
		}
	}
}
