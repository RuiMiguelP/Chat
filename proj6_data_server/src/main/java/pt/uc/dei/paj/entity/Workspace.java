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

import pt.uc.dei.paj.dto.UserDto;
import pt.uc.dei.paj.dto.WorkspaceDto;

@Entity
@Table(name = "workspace")
@NamedQueries({
	@NamedQuery(name = "Workspace.findById", query = "SELECT wrk FROM Workspace wrk WHERE wrk.id=:id"),
	@NamedQuery(name = "Workspace.findAll", query = "SELECT wrk FROM Workspace wrk"),
	@NamedQuery(name = "Workspace.findWorkspacesByUser", query = "SELECT wrk FROM Workspace wrk WHERE wrk.user.id=:userId"),
	@NamedQuery(name = "Workspace.findWorkspacesByCreatorAndState", query = "SELECT wrk FROM Workspace wrk WHERE wrk.user.id=:userId AND isActive=:isActive"),
	@NamedQuery(name = "Workspace.findWorkspacesByUserAndState", query = "SELECT wrk FROM Workspace wrk WHERE :user MEMBER OF wrk.users AND isActive=:isActive"),
	@NamedQuery(name = "Workspace.findUsersByWorkspace", query = "SELECT wrk FROM Workspace wrk WHERE wrk.id=:workspaceId"),
})
public class Workspace implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private int id;
	
	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;
	
	@Column(nullable = false)
	private String title;
	
	private boolean isActive;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_workspace", 
	joinColumns = @JoinColumn(name = "fk_workspace_id"),
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public WorkspaceDto toDto() {
		UserDto userDto = user.toDto();
		ArrayList<UserDto> usersDto = new ArrayList<>();
		
		for (User userEntity: users) {
			usersDto.add(userEntity.toDto());
		}

		return new WorkspaceDto(id, title, isActive, userDto, usersDto);
	}
}
