package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
@Data
public class User {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	@Column(unique = true,length = 20)
	private String username;
	@Column(unique = false,length = 400)
	private String password;
	@Column(unique = true,length = 30)
	private String email;
	@Column(unique = false)
	private String name;
	@Column(name= "role")
	private String role;
	@Column(name="class")
	private Long classId;
	@Column(name="class_a_b_c")
	private String classABC;
	@Column(name="is_active")
	private boolean isActive;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getClassABC() {
		return classABC;
	}

	public void setClassABC(String classABC) {
		this.classABC = classABC;
	}

	public List<Channels> getChannels() {
		return channels;
	}

	public void setChannels(List<Channels> channels) {
		this.channels = channels;
	}

	public java.util.Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(java.util.Date timestamp) {
		this.timestamp = timestamp;
	}

	@ManyToMany
@JoinTable(
    name = "user_channels",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "channel_id")
)
private List<Channels> channels = new ArrayList<>();

	@CreationTimestamp
	private java.util.Date timestamp;

	public void setActive(boolean b) {
		this.isActive = b;
		// TODO Auto-generated method stub
		
	}
	public boolean getActive() {
		return this.isActive;
	}



}
