package pt.uc.dei.paj.ws;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import pt.uc.dei.paj.dao.NotificationDao;
import pt.uc.dei.paj.dto.MessageDto;
import pt.uc.dei.paj.dto.NotificationDto;
import pt.uc.dei.paj.entity.Channel;
import pt.uc.dei.paj.entity.Notification;
import pt.uc.dei.paj.entity.User;
import pt.uc.dei.paj.entity.Workspace;
import pt.uc.dei.paj.util.Constants;

@ApplicationScoped
public class NotificationBean {

	@Inject
	NotificationDao notificationDao;

	public void createNotification(Channel channel, User user, Workspace workspace, String bodyMessage) {
		
		Notification notification = new Notification();
		notification.setMessage(bodyMessage);
		notification.setCreatedDate(LocalDateTime.now());
		notification.setChannel(channel);
		notification.setUser(user);
		notification.setWorkspace(channel.getWorkspace());
		
		if (channel.getIsDirect()) {
			notification.setNotificationType(Constants.CONVERSATION);
		} else {
			notification.setNotificationType(Constants.CHANNEL);
		}
		
		notificationDao.persist(notification);
		NotificationDto notificationDto = notification.toDto();
		
		for (User usr: channel.getUsers()) {
			if (usr.getId() != user.getId()) {
				notificationDto.setUserTarget(usr.getId());
				NotificationEndpoint.send(notificationDto);
			}
		}
	}

	public void createNotificationChangeChannel(Channel channel, User user, Workspace workspace,
			String typeChannelAction) {
		
		Notification notification = new Notification();
		notification.setCreatedDate(LocalDateTime.now());
		notification.setMessage(Constants.CHANNEL_ACTION);
		notification.setChannel(channel);
		notification.setUser(user);
		notification.setWorkspace(channel.getWorkspace());
		notification.setNotificationType(typeChannelAction);
		
		notificationDao.persist(notification);
		NotificationDto notificationDto = notification.toDto();
		
		for (User usr: workspace.getUsers()) {
			if (usr.getId() != user.getId()) {
				notificationDto.setUserTarget(usr.getId());
				NotificationEndpoint.send(notificationDto);
			}
		}
	}
}
