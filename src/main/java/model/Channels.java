package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@Table(name = "channels")
@AllArgsConstructor
@NoArgsConstructor
public class Channels {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String name;
    @Column( nullable = false)
    private String description;
    private LocalTime now = LocalTime.now();
    @Column(name="class")
    private Long classiD;
    @Column(name="class_a_b_c")
    private String classABC;
	
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalTime getNow() {
		return now;
	}

	public void setNow(LocalTime now) {
		this.now = now;
	}

	public Long getClassiD() {
		return classiD;
	}

	public void setClassiD(Long classiD) {
		this.classiD = classiD;
	}

	public String getClassABC() {
		return classABC;
	}

	public void setClassABC(String classABC) {
		this.classABC = classABC;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@ManyToMany(mappedBy = "channels", fetch = FetchType.LAZY)
    @JsonIgnore
private List<User> users;
@OneToMany(mappedBy = "channel",fetch = FetchType.LAZY)
private List<MessgessssK> messgessssKs;


public List<MessgessssK> getMessgessssKs() {
	return this.messgessssKs;
}

    

}
