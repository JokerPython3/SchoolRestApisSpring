package Controllre.AuthControllre.WebSocket;

import Reposteryes.MessagessRepo;
import Reposteryes.UserReposteryes;
import Service.ChatService;
import Service.MessageService;
import model.Channels;
import model.MessgessssK;
import model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.javapoet.LordOfTheStrings.ReturnBuilderSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import JwtsManager.MainJwts;

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
    private final MainJwts mainJwts;
    public MessageRest(MessageService messageService, ChatService chatService,UserReposteryes userReposteryes,MainJwts mainJwts) {
        this.messageService = messageService;
        this.chatService = chatService;
        this.userReposteryes =userReposteryes;
        this.mainJwts = mainJwts;
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
@PostMapping("/upload/video")
public MessgessssK uploadVideoMessage(
        @RequestParam("text") String text,
        @RequestParam("sender") String sender,
        @RequestParam("channelId") Long channelId,
        @RequestParam(value = "video", required = false) MultipartFile videoFile,
        @RequestHeader("Authorization") String token) { 
    
    DTO.Messages messages = new DTO.Messages();
    messages.setContent(text);
    messages.setSender(sender);
    messages.setChannelId(channelId);
    messages.setVideo(videoFile);           
    MessgessssK savedMessage = chatService.sendMessage(messages,token.replace("Bearer", ""));
    return savedMessage;
}
@PostMapping("/delete/{idMessage}/{channelId}/{userId}")
public ResponseEntity<Map<String,Object>> deleteMessagees(
        @PathVariable Long idMessage,
        @PathVariable Long channelId,
        @PathVariable Long userId) {    
    boolean deleted = chatService.DeleteMessage(idMessage, channelId, userId);
    if (deleted) {
        return ResponseEntity.ok(
                Map.of(
                        "data", Map.of( 
                                "deleted", true
                        ),
                        "message", "Message deleted successfully",
                        "status", 200           
                    ) // بطلنا نوبمرش صطناعي يبرمش حسن
        );
    } else {
        return ResponseEntity.status(400).body(   
                Map.of(
                        "data", Map.of( 
                                "deleted", false
                        ),
                        "message", "Failed to delete the message",
                        "status", 400
                    ));
        
    }}
    @PostMapping("/edit/message/{idMessage}/{channelId}/{userId}/{content}")
    public ResponseEntity<Map<String,Object>> editMessagees(@PathVariable Long idMessage,
                                                          @PathVariable Long channelId,
                                                          @PathVariable Long userId,
                                                          @PathVariable String content) {
        boolean edited = chatService.EditMessage(idMessage, channelId, userId, content);
        if (edited) {
            return ResponseEntity.ok(
                    Map.of(
                            "data", Map.of(
                                    "edited", true
                            ),
                            "message", "Message edited successfully",
                            "status", 200
                    )
            );
        } else {
            return ResponseEntity.status(400).body(
                    Map.of(
                            "data", Map.of(
                                    "edited", false
                            ),
                            "message", "Failed to edit the message",
                            "status", 400
                    )
            );
        }
    }
    @PostMapping("/leave/{channelId}/{userId}")
    public ResponseEntity<Map<String,Object>> leaveisAtroo(@PathVariable Long channelId,@PathVariable Long userId){
        boolean ar = chatService.LeaveChannels(channelId, userId);
        if(ar){
            return  ResponseEntity.ok(
                    Map.of(
                            "data",Map.of(
                                    "left",true
                            ),
                            "message","User left the channel successfully",
                            "status",200
                    )
            );
        } else {
            return ResponseEntity.status(400).body(
                    Map.of(
                            "data",Map.of(
                                    "left",false
                            ),
                            "message","Failed to leave the channel",
                            "status",400
                    )
            );
        }
    }
    @GetMapping("/get/users/active/{channelid}/")
    public ResponseEntity<Map<String, Object>> getUsersActiveInChannel(@PathVariable("channelid") Long channelId){
    	try {
    	List<User> users = chatService.getActiveUsersInChannel(channelId);
    	return ResponseEntity.ok(Map.of("data",Map.of("message","successfully","users",users),"message","success"));
    
    	}catch (Exception e) {
			return ResponseEntity.status(302).body(Map.of("data",Map.of("message","channel not found","List_users",List.of("users",null)),"message","error"));
		}}
    @GetMapping("/filter/search/keyword/{channelid}/{keyword}/{userId}")
    public ResponseEntity<Map<String, Object>> atroIsAtro(@PathVariable("channelid") Long channelId,@PathVariable("keyword") String keyword,@PathVariable("userId") Long userId){
    	try {
    		List<MessgessssK> eeKs = chatService.filterMessagesChannels(channelId, keyword, userId);
    		return ResponseEntity.ok(Map.of("data",Map.of("message","successfully","messages",eeKs),"message","success"));
    	}catch (Exception e) {
    		return ResponseEntity.status(302).body(Map.of("data",Map.of("message","channel not found","messages",List.of("users",null)),"message","error"));
			
		}
    	
    	
    }
    @GetMapping("/get/messages/channel/{channelId}/{userId}")
    public ResponseEntity<Map<String,Object>> MessagesChannels(@PathVariable("channelId") Long channelId,@PathVariable("userID") Long userId){
    	try {
    	List<MessgessssK> messgessssKs = chatService.getAllMessagesInChannel(channelId, userId);
    	return ResponseEntity.ok(Map.of("data",Map.of("message","successfully","messages",messgessssKs),"message","success"));
    			}
    	catch (Exception e) {
    		return ResponseEntity.status(302).body(Map.of("data",Map.of("message","channel not found","messages",List.of("users",null)),"message","error"));
			// TODO: handle exception
		}
    }
@GetMapping("/get/channel/info/{channelId}/{userId}")
public ResponseEntity<Map<String, Object>> getChannelInfo(@PathVariable("channelId") Long channelId, @PathVariable("userId") Long userId) {
    try {
        Channels channel = chatService.channelInfo(channelId, userId);
        return ResponseEntity.ok(Map.of("data", Map.of("message", "successfully", "channel", channel), "message", "success"));
    } catch (Exception e) {
        return ResponseEntity.status(302).body(Map.of("data", Map.of("message", "channel not found"), "message", "error"));
    }
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

// بعد شنضيف 