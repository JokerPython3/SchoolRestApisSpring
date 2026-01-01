package model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Entity
@Table(name = "tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokens {
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Id
    private  Long id;
    @Column(unique = true,nullable = false,length = 400)
    private  String token;
    private Instant ex;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    //ont to one اذا تريد بس ريفرش توكن واحد للحساب


}
