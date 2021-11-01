package pt.uc.dei.paj.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class ChannelDto extends AbstractDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String title;
		
	private boolean isDirect;
	
	private boolean isActive;
	
	private WorkspaceDto workspace;
	
	private UserDto user;

	private ArrayList<UserDto> users = new ArrayList<UserDto>();
	
	public ChannelDto(int id, String title, Boolean isDirect, boolean isActive, UserDto user, ArrayList<UserDto> users) {
		super();
		this.id = id;
		this.title = title;
		this.isDirect = isDirect;
		this.isActive = isActive;
		this.user = user;
		this.users = users;
	}

	public ChannelDto(int id, String title, Boolean isDirect, boolean isActive,
			WorkspaceDto workspace, UserDto user, ArrayList<UserDto> users) {
		super();
		this.id = id;
		this.title = title;
		this.isDirect = isDirect;
		this.isActive = isActive;
		this.workspace = workspace;
		this.user = user;
		this.users = users;
	}

	public ChannelDto(int id, String title, Boolean isDirect, Boolean isActive, WorkspaceDto workspace) {
		super();
		this.id = id;
		this.title = title;
		this.isDirect = isDirect;
		this.isActive = isActive;
		this.workspace = workspace;
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

	public WorkspaceDto getWorkspace() {
		return workspace;
	}

	public void setWorkspace(WorkspaceDto workspace) {
		this.workspace = workspace;
	}

	public ArrayList<UserDto> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<UserDto> users) {
		this.users = users;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
}
