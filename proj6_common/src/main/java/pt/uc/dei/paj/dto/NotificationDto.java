package pt.uc.dei.paj.dto;

import java.io.Serializable;

public class NotificationDto implements Serializable {
private static final long serialVersionUID = 1L;
	
	private int userTarget;
	private int channelId;
	private int workspaceId;
	private String senderMessage;
	private String notificationType;
	
	public NotificationDto() {
	}
	
	public NotificationDto(int userTarget, int channelId, int workspaceId, String notificationType) {
		super();
		this.userTarget = userTarget;
		this.channelId = channelId;
		this.workspaceId = workspaceId;
		this.notificationType = notificationType;
	}

	public NotificationDto(int channelId, int workspaceId, String senderMessage, String notificationType) {
		super();
		this.channelId = channelId;
		this.workspaceId = workspaceId;
		this.senderMessage = senderMessage;
		this.notificationType = notificationType;
	}

	public int getUserTarget() {
		return userTarget;
	}

	public void setUserTarget(int userTarget) {
		this.userTarget = userTarget;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(int workspaceId) {
		this.workspaceId = workspaceId;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getSenderMessage() {
		return senderMessage;
	}

	public void setSenderMessage(String senderMessage) {
		this.senderMessage = senderMessage;
	}
}
