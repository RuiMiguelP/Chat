package pt.uc.dei.paj.dto;

import java.io.Serializable;
import java.util.ArrayList;



public class WorkspaceDto extends AbstractDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String title;
	
	private boolean isActive;

	private UserDto user;
	
	private ArrayList<UserDto> users = new ArrayList<UserDto>();

	public WorkspaceDto(int id, String title, boolean isActive, UserDto user, ArrayList<UserDto> users) {
		super();
		this.id = id;
		this.title = title;
		this.isActive = isActive;
		this.user = user;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public ArrayList<UserDto> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<UserDto> users) {
		this.users = users;
	}
}
