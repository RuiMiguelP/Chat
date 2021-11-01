package pt.uc.dei.paj.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pt.uc.dei.paj.dto.ChannelDto;
import pt.uc.dei.paj.dto.MessageDto;
import pt.uc.dei.paj.dto.UserDto;
import pt.uc.dei.paj.dto.WorkspaceDto;

@Entity
@Table(name = "message")
@NamedQueries({ 
	@NamedQuery(name = "Message.findMessagesByChannel", query = "SELECT msg FROM Message msg WHERE channel.id=:channelId")
})
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private int id;
	
	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;
	
	@Column(name = "body_message", nullable = false)
	private String bodyMessage;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "channel_id", nullable = false)
	private Channel channel;
	
	@ManyToOne
	@JoinColumn(name = "workspace_id", nullable = false)
	private Workspace workspace;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getBodyMessage() {
		return bodyMessage;
	}

	public void setBodyMessage(String bodyMessage) {
		this.bodyMessage = bodyMessage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public Workspace getWorkspace() {
		return workspace;
	}

	public void setWorkspace(Workspace workspace) {
		this.workspace = workspace;
	}

	public MessageDto toDto() {
		UserDto userDto = user.toDto();
		ChannelDto channelDto = channel.toDto();

		return new MessageDto(id, Timestamp.valueOf(createdDate), bodyMessage, userDto, channelDto);
	}
}
