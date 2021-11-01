package pt.uc.dei.paj.dto;

import java.io.Serializable;

public class UserDto extends AbstractDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private boolean isActive;
	private boolean isAdmin;
	private String email;
	private String password;
	private String token;

	
	public UserDto() {
		super();
	}
	
	public UserDto(int id, String name, String email, boolean isActive, boolean isAdmin, String token) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.isActive = isActive;
		this.isAdmin = isAdmin;
		this.token = token;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
}
