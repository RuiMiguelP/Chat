package pt.uc.dei.paj.entity;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pt.uc.dei.paj.dto.ChannelDto;
import pt.uc.dei.paj.dto.UserDto;
import pt.uc.dei.paj.dto.WorkspaceDto;

@Entity
@Table(name = "channel")
@NamedQueries({
	@NamedQuery(name = "Channel.findById", query = "SELECT cha FROM Channel cha WHERE cha.id=:id"),
	@NamedQuery(name = "Channel.findAllChannels", query = "SELECT cha FROM Channel cha WHERE cha.isDirect=false"),
	@NamedQuery(name = "Channel.findAllConversations", query = "SELECT cha FROM Channel cha WHERE cha.isDirect=true"),
	@NamedQuery(name = "Channel.findChannelsByWorkspace", query = "SELECT cha FROM Channel cha WHERE cha.workspace.id=:workspaceId AND cha.isActive=:isActive AND cha.isDirect=:isDirect"),
	@NamedQuery(name = "Channel.findConversationByWorkspaceAndUsers", query = "SELECT cha FROM Channel cha WHERE cha.workspace.id=:workspaceId AND :user MEMBER OF cha.users AND :otherUser MEMBER OF cha.users AND cha.isDirect=:isDirect"),
	}
)
public class Channel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private int id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;
	
	private Boolean isDirect;
	
	private Boolean isActive;
	
	@ManyToOne
	@JoinColumn(name = "workspace_id", nullable = false)
	private Workspace workspace;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_channel", joinColumns = @JoinColumn(name = "fk_channel_id"),
	inverseJoinColumns = @JoinColumn(name = "fk_user_id"))
	private Set<User> users = new HashSet<User>();
	
	public void addUser(User user) {
        this.users.add(user);
    }
 
    public void removeUser(User user) {
		User unmark = null;
		try {
			for (User userEntity: users) {
	    		if (userEntity.getId() == user.getId()) {
	    			unmark = userEntity;
	    			break;
	    		}
	    	}
			this.users.remove(unmark);
		} catch (Exception e) {
			System.out.println("Error: Workspace.removeUser > " + e);
		}
    }

	public Boolean getIsDirect() {
		return isDirect;
	}

	public void setIsDirect(Boolean isDirect) {
		this.isDirect = isDirect;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isDirect() {
		return isDirect;
	}

	public void setDirect(boolean isDirect) {
		this.isDirect = isDirect;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public Workspace getWorkspace() {
		return workspace;
	}

	public void setWorkspace(Workspace workspace) {
		this.workspace = workspace;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public ChannelDto toDto() {
		UserDto userDto = user.toDto();
		ArrayList<UserDto> usersDto = new ArrayList<>();
		
		for (User userEntity: users) {
			usersDto.add(userEntity.toDto());
		}

		return new ChannelDto(id, title, isDirect, isActive, userDto, usersDto);
	}
	
	public ChannelDto toDtoStat() {
		WorkspaceDto workspaceDto = workspace.toDto();

		return new ChannelDto(id, title, isDirect, isActive, workspaceDto);
	}
}
