package pt.uc.dei.paj.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import pt.uc.dei.paj.dto.UserDto;

@Entity
@Table(name = "user")
@NamedQueries({
	@NamedQuery(name = "User.findAll", query = "SELECT usr FROM User usr"),
	@NamedQuery(name = "User.findAllByAdmin", query = "SELECT usr FROM User usr WHERE usr.isAdmin=:isAdmin"),
	@NamedQuery(name = "User.findById", query = "SELECT usr FROM User usr WHERE usr.id=:id"),
	@NamedQuery(name = "User.findByEmail", query = "SELECT usr FROM User usr WHERE usr.email=:email"),
	@NamedQuery(name = "User.findByToken", query = "SELECT usr FROM User usr WHERE usr.token=:token"),
	@NamedQuery(name = "User.findByEmailAndToken", query = "SELECT usr FROM User usr WHERE usr.email=:email AND usr.token=:token"),
	@NamedQuery(name = "User.findByAdmin", query = "SELECT usr FROM User usr WHERE usr.isAdmin=:admin"),
})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private int id;
	
	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	private String saltPassword;
	
	@Column(nullable = true, unique = true)
	private String token;
	
	private boolean isActive;

	private boolean isAdmin;

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
	
	public String getSaltPassword() {
		return saltPassword;
	}

	public void setSaltPassword(String saltPassword) {
		this.saltPassword = saltPassword;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public UserDto toDto() {
		return new UserDto(id, name, email, isActive, isAdmin, token);
	}
}
