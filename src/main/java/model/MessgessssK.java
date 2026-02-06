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
	// @Id
	// @GeneratedValue(strategy =  GenerationType.IDENTITY)
	// private Long MessageId;
    private Channels channel;
    private String content;
    private String sender;
    private String textImages;
    private LocalTime now = LocalTime.now();
    private String imagePath;
    @Lob // لخزن ملفات جبيره
    private byte[] image;
	 // ححول صوره لي byte
	@Lob
	private byte[] video;
	@Lob
	private byte[] voice;
    @ManyToOne
    private User user;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Channels getChannel() {
		return channel;
	}
	public void setChannel(Channels channel) {
		this.channel = channel;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getTextImages() {
		return textImages;
	}
	public void setTextImages(String textImages) {
		this.textImages = textImages;
	}
	public LocalTime getNow() {
		return now;
	}
	public void setNow(LocalTime now) {
		this.now = now;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public byte[] getVideo() {
		return video;
	}
	public void setVideo(byte[] video) {
		this.video = video;
	}
	public byte[] getVoice() {
		return voice;
	}
	public void setVoice(byte[] voice) {
		this.voice = voice;
	}
	

}
