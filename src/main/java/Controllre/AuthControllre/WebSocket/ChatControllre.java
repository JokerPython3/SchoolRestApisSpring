package Controllre.AuthControllre.WebSocket;

import DTO.Messages;
import Reposteryes.MessagessRepo;
import Service.ChatService;
import io.jsonwebtoken.io.IOException;
import model.Channels;
import model.MessgessssK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalTime;
import java.util.List;
@Controller
public class ChatControllre {

    private final ChatService chatService;
   

    public ChatControllre(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat.send")
public void send(Messages messages, @RequestHeader("Authorization") String access){
    String token = access.replace("Bearer ", "");
    // token = token.substring(7);
    
 
    try{
chatService.sendMessage(messages,token);
    }catch(IOException e){
        e.printStackTrace();
    }
    
}
@PostMapping("/chat/history/{userId}")
public List<Channels> channelsUser(@PathVariable Long userId) {
    return chatService.getUserChannels1(userId);
}
@MessageMapping("/active/users/{userId}")
public void activeUsers(@PathVariable Long userId) {
    chatService.isUserActive(userId);
}
@MessageMapping("/unactive/users/{userId}")
public void unactiveUsers(@PathVariable Long userId) {
    chatService.unsetUserActive(userId);
}

}

