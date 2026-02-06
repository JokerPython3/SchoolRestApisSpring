package Controllre.AuthControllre.WebSocket;

import Service.ChannelService;
import model.Channels;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channesl")
public class WebSocketControllre {
    private  final ChannelService channelService;
    public WebSocketControllre(ChannelService channelService) {
        this.channelService = channelService;
    }
    @PostMapping
    public ResponseEntity<Channels> createChannel(Channels channel){
        Channels createdChannel = channelService.createChannel(channel);
        return ResponseEntity.ok(createdChannel);
    }
    
}
