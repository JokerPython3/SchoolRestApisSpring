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
	@ManyToMany
@JoinTable(
    name = "user_channels",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "channel_id")
)
private List<Channels> channels = new ArrayList<>();

	@CreationTimestamp
	private java.util.Date timestamp;



}
