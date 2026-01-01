package model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

import java.time.LocalTime;

@Data // فايدته يعمل getter and setter جاهزين بدل ماتكتبهم او تسوي @Getter و @Setter فوق كلاص
@Entity
public class MessgessssK {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  Long id;
    @ManyToOne
    private Channels channel;
    private String content;
    private String sender;
    private String textImages;
    private LocalTime now = LocalTime.now();
    private String imagePath;
    @Lob // لخزن ملفات جبيره
    private byte[] image; // ححول صوره لي byte
    @ManyToOne
    private User user;

}
