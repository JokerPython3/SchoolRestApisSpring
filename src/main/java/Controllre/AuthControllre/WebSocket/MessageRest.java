package Controllre.AuthControllre.WebSocket;

import Reposteryes.MessagessRepo;
import Reposteryes.UserReposteryes;
import Service.ChatService;
import Service.MessageService;
import model.Channels;
import model.MessgessssK;
import model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/messages")
public class MessageRest {

    private final ChatService chatService;

    private final MessageService messageService;
    private final UserReposteryes userReposteryes;
    public MessageRest(MessageService messageService, ChatService chatService,UserReposteryes userReposteryes) {
        this.messageService = messageService;
        this.chatService = chatService;
        this.userReposteryes =userReposteryes;
    }
    @Autowired
    private MessagessRepo messagessRepo;

    @GetMapping("/{channelId}")
    public List<MessgessssK> history(@PathVariable Long channelId) {
        return messageService.getHistory(channelId);
        // channel id => هو ايدي جانل بي قاعده بيانات ال id بي اول جدول
    }
    // join channel rest api need user id and channel id  and اترو يتصل بكه
    @GetMapping("/join/{channelId}/{userId}")
    public ResponseEntity<Map<String,Object>> JoinesChann(@PathVariable Long channelId, @PathVariable Long userId) {
        if(chatService.joinChannel(channelId, userId)){
            return ResponseEntity.ok(
                    Map.of(
                            "data",Map.of(
                                    "joined",true
                            ),
                            "message","User joined the channel successfully",
                            "status",200
                    )
            );
        } else {
            return ResponseEntity.status(400).body(
                    Map.of(
                            "data",Map.of(
                                    "joined",false
                            ),
                            "message","Failed to join the channel",
                            "status",400
                    )
            );

        }
    }
//     @PostMapping("/upload")
// public MessgessssK uploadMessage(
//         @RequestParam("text") String text,
//         @RequestParam(value = "image", required = false) MultipartFile file) throws IOException {

//     MessgessssK message = new MessgessssK();
//     message.setTextImages(text);

//     if (file != null && !file.isEmpty()) {
    
//         String folder = "uploads/";
//         Path path = Paths.get(folder + file.getOriginalFilename());
//         Files.createDirectories(path.getParent());
//         Files.write(path, file.getBytes());

//         message.setImagePath(path.toString());
//         message.setImage(file.getBytes()); 
//     }

//     return messagessRepo.save(message);
// }
@PostMapping("/upload")
public MessgessssK uploadMessage(
        @RequestParam("text") String text,
        @RequestParam("sender") String sender,
        @RequestParam("channelId") Long channelId,
        @RequestParam(value = "image", required = false) MultipartFile file,
        @RequestHeader("Authorization") String token) {

    DTO.Messages messages = new DTO.Messages();
    messages.setContent(text);
    messages.setSender(sender);
    messages.setChannelId(channelId);
    messages.setImage(file);

    
    MessgessssK savedMessage = chatService.sendMessage(messages,token.replace("Bearer", ""));

    return savedMessage;
}
//  @PostMapping("/upload")
//     public MessgessssK uploadMessage(
//             @RequestParam("text") String text,
//             @RequestParam("sender") String sender,
//             @RequestParam("channelId") Long channelId,
//             @RequestParam(value = "image", required = false) MultipartFile file,@RequestHeader("Authorization") String token) {

//         DTO.Messages messages = new DTO.Messages();
//         messages.setContent(text);
//         messages.setSender(sender);
//         messages.setChannelId(channelId);
//         messages.setImage(file);

//         chatService.sendMessage(messages, token.replace("Bearer", "")); 

//         return new MessgessssK(); 
//     }
    // @GetMapping("/del/{channelId}/{userId}")
    // public ResponseEntity<Map<String,Object>> delChannels(@PathVariable Long channelId,@PathVariable Long userId){
        

    // }
}
