package Controllre.AuthControllre.WebSocket;

import DTO.Messages;
import Service.ChannelService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class Atro {
    private final ChannelService channelService;

    public Atro(ChannelService channelService) {
        this.channelService = channelService;
    }

    // @MessageMapping("/sendMessage")
    // public void handleMessage(Messages message) {
    //     channelService.sendMessage(message);
    // }
}
