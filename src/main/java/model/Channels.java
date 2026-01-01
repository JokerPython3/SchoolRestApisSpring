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
    
    @ManyToMany(mappedBy = "channels", fetch = FetchType.LAZY)
    @JsonIgnore
private List<User> users;

    

}
