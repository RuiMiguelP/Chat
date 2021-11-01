package pt.uc.dei.paj.entity;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pt.uc.dei.paj.dto.ChannelDto;
import pt.uc.dei.paj.dto.MessageDto;
import pt.uc.dei.paj.dto.NotificationDto;
import pt.uc.dei.paj.dto.UserDto;

@Entity
@Table(name = "history_notification")
@NamedQueries({
	@NamedQuery(name = "Notification.findById", query = "SELECT ntf FROM Notification ntf WHERE ntf.id=:id")

})
public class Notification implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private int id;
	
	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;

	@Column(name = "message", nullable = false)
	private String message;
	
	@Column(name = "notification_type", updatable = false)
	private String notificationType;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "workspace_id", nullable = false)
	private Workspace workspace;
	
	@ManyToOne
	@JoinColumn(name = "channel_id", nullable = false)
	private Channel channel;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Workspace getWorkspace() {
		return workspace;
	}

	public void setWorkspace(Workspace workspace) {
		this.workspace = workspace;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public NotificationDto toDto() {
		return new NotificationDto(channel.getId(), workspace.getId(), user.getName(), notificationType);
	}
}
