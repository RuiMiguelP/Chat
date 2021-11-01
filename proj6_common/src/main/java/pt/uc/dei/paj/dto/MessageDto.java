package pt.uc.dei.paj.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class MessageDto extends AbstractDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	
	private Timestamp createdDate;
	
	private String bodyMessage;

	private UserDto userDto;
	
	private ChannelDto channelDto;
	
	public MessageDto(int id, Timestamp createdDate, String bodyMessage, UserDto userDto) {
		super();
		this.id = id;
		this.createdDate = createdDate;
		this.bodyMessage = bodyMessage;
		this.userDto = userDto;
	}
	
	public MessageDto(int id, Timestamp createdDate, String bodyMessage, UserDto userDto, ChannelDto channelDto) {
		super();
		this.id = id;
		this.createdDate = createdDate;
		this.bodyMessage = bodyMessage;
		this.userDto = userDto;
		this.channelDto = channelDto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getBodyMessage() {
		return bodyMessage;
	}

	public void setBodyMessage(String bodyMessage) {
		this.bodyMessage = bodyMessage;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public ChannelDto getChannelDto() {
		return channelDto;
	}

	public void setChannelDto(ChannelDto channelDto) {
		this.channelDto = channelDto;
	}
}
