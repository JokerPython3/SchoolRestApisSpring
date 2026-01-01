package DTO;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Messages {
    private String content;
    private String sender;
    private Long channelId;
    private MultipartFile image; // قتياري صوره يعني
    
}
