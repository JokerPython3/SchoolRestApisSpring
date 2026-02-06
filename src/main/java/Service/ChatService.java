package Service;

// import DTO.Messages;
// import JwtsManager.MainJwts;
// import Reposteryes.ChannelRepo;
// import Reposteryes.MessagessRepo;
// import Reposteryes.UserReposteryes;
// import io.jsonwebtoken.lang.Objects;
// import jakarta.transaction.Transactional;
// import model.MessgessssK;
// import model.User;
// import model.Channels;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.stereotype.Service;

// import java.time.LocalTime;
// import java.util.List;
// import DTO.Messages;
// import JwtsManager.MainJwts;
// import Reposteryes.ChannelRepo;
// import Reposteryes.MessagessRepo;
// import Reposteryes.UserReposteryes;
// import io.swagger.v3.oas.models.Paths;
// import jakarta.persistence.criteria.Path;
// import model.Channels;
// import model.MessgessssK;
// import model.User;

// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.web.multipart.MultipartFile;

// import java.nio.file.Files;
// import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import DTO.Messages;
import JwtsManager.MainJwts;
import Reposteryes.ChannelRepo;
import Reposteryes.MessagessRepo;
import Reposteryes.UserReposteryes;
import model.Channels;
import model.MessgessssK;
import model.User;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
@Service
@Transactional
public class ChatService {

    private final MessagessRepo messagessRepo;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChannelRepo channelRepo;
    private final MainJwts mainJwts;
    private final UserReposteryes userReposteryes;

    public ChatService(
            MessagessRepo messagessRepo,
            SimpMessagingTemplate simpMessagingTemplate,
            ChannelRepo channelRepo,
            MainJwts mainJwts,
            UserReposteryes userReposteryes) {

        this.messagessRepo = messagessRepo;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.channelRepo = channelRepo;
        this.mainJwts = mainJwts;
        this.userReposteryes = userReposteryes;
    }
    public MessgessssK sendMessage(Messages messages, String access)  {
    MessgessssK entity = new MessgessssK();
    try{
        Long userId = mainJwts.getUserIdFromAccessToken(access);

        User user = userReposteryes.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!"TEACHER".equals(user.getRole())) {
            throw new RuntimeException("You are not allowed to send messages");
        }

        Channels channel = channelRepo.findById(messages.getChannelId())
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        entity.setSender(messages.getSender());
        entity.setContent(messages.getContent());
        entity.setNow(LocalTime.now());
        entity.setChannel(channel);
        entity.setUser(user);

        MultipartFile file = messages.getImage();
        if (file != null && !file.isEmpty()) {
            String folder = "uploads/";
            Path path = Paths.get(folder + file.getOriginalFilename());
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            entity.setImagePath(path.toString());
            entity.setImage(file.getBytes());
        }
        MultipartFile videoFile = messages.getVideo();
        if (videoFile != null && !videoFile.isEmpty()) {
            String folder = "uploads/videos/";
            Path path = Paths.get(folder + videoFile.getOriginalFilename());
            Files.createDirectories(path.getParent());
            Files.write(path, videoFile.getBytes());    
            entity.setVideo(videoFile.getBytes());
        }

        messagessRepo.save(entity);

        simpMessagingTemplate.convertAndSend("/topic/channel." + channel.getId(), messages);

    } catch(IOException e){
        e.printStackTrace();
    }

    return entity; 
}

// public void sendMessage(Messages messages, String access)  {
//     try{
//            Long userId = mainJwts.getUserIdFromAccessToken(access);

//             User user = userReposteryes.findById(userId)
//                     .orElseThrow(() -> new RuntimeException("User not found"));

//             if (!"TEACHER".equals(user.getRole())) {
//                 throw new RuntimeException("You are not allowed to send messages");
//             }

//             Channels channel = channelRepo.findById(messages.getChannelId())
//                     .orElseThrow(() -> new RuntimeException("Channel not found"));

//             MessgessssK entity = new MessgessssK();
//             entity.setSender(messages.getSender());
//             entity.setContent(messages.getContent());
//             entity.setNow(LocalTime.now());
//             entity.setChannel(channel);
//             entity.setUser(user);

           
//             MultipartFile file = messages.getImage();
//             if (file != null && !file.isEmpty()) {
//                 String folder = "uploads/";
//                 Path path = Paths.get(folder + file.getOriginalFilename());
//                 Files.createDirectories(path.getParent());
//                 Files.write(path, file.getBytes());

//                 entity.setImagePath(path.toString());
//                 entity.setImage(file.getBytes());
//             }

//             messagessRepo.save(entity);

//             simpMessagingTemplate.convertAndSend("/topic/channel." + channel.getId(), messages);
//     }catch(IOException e){
//         e.printStackTrace();
//     }

    
// }
    // public void sendMessage(Messages messages, String access) {

    //     Long userId = mainJwts.getUserIdFromAccessToken(access);

    //     User user = userReposteryes.findById(userId)
    //             .orElseThrow(() -> new RuntimeException("User not found"));

    //     if (!"TEACHER".equals(user.getRole())) {
    //         throw new RuntimeException("You are not allowed to send messages");
    //     }

    //     Channels channel = channelRepo.findById(messages.getChannelId())
    //             .orElseThrow(() -> new RuntimeException("Channel not found"));

    //     MessgessssK entity = new MessgessssK();
    //     entity.setSender(messages.getSender());
    //     entity.setContent(messages.getContent());
    //     entity.setNow(LocalTime.now());
    //     entity.setChannel(channel);
    //     entity.setUser(user);
    //     // نربط رساله بل مستخدم

    //     messagessRepo.save(entity);

    //     simpMessagingTemplate.convertAndSend(
    //             "/topic/channel." + channel.getId(),
    //             messages
    //     );
    // }

    public boolean joinChannel(Long channelId, Long userId) {

        User user = userReposteryes.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Channels channel = channelRepo.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));

        if (!Objects.equals(user.getClassId(), channel.getClassiD()) ||
            !Objects.equals(user.getClassABC(), channel.getClassABC())) {
            return false;
        }
        if(channel.getUsers().contains(user)){
            return true;
        }

        channelRepo.joinChannel(channelId, userId);
        return true;
    }

    @Transactional(readOnly = true)
    public List<Channels> getUserChannels(Long userId) {
        return channelRepo.findChannelsByUserId(userId);
    }
     @Transactional(readOnly = true)
    public boolean DeleteMessage(Long channelId,Long userId,Long idMessage){
        User user = userReposteryes.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Channels channel = channelRepo.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        MessgessssK messgessssK = messagessRepo.findById(idMessage).orElseThrow(() -> new RuntimeException("Message not found"));
        if(joinChannel(channelId, userId)){
            
            List<MessgessssK> channels = channel.getMessgessssKs();
            for(MessgessssK channelss: channels){
                if(channelss.getId().equals(idMessage)){
                    messagessRepo.deleteById(idMessage);
                    break;
                }

            }
            return true;

        }else{
            return false;
        }
        
        
        
    }// id => id in بي اول جدول 
    public boolean EditMessage(Long channelId,Long userId,Long idMessage,String content){
        User user = userReposteryes.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Channels channel = channelRepo.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        MessgessssK messgessssK = messagessRepo.findById(idMessage).orElseThrow(() -> new RuntimeException("Message not found"));
        if(joinChannel(channelId, userId)){ 
            
            List<MessgessssK> channels = channel.getMessgessssKs();
            for(MessgessssK channelss: channels){
                if(channelss.getId().equals(idMessage)){
                    channelss.setContent(content);
                    messagessRepo.save(channelss);
                    break;
                }

            }
            return true;
        } else {
            return false;
        }}
    public boolean LeaveChannels(Long channelId,Long userId){
        User user = userReposteryes.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Channels channel = channelRepo.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        if(joinChannel(channelId, userId)){ 
            List<User> users = channel.getUsers();
            for(User userr: users){
                if(userr.getId().equals(userId)){
                    users.remove(userr);
                    channel.setUsers(users);
                    channelRepo.save(channel);
                    break;
                }
                //بطلنا 

            }
            return true;
        } else {
            return false;
        }}
            public List<Channels> getUserChannels1(Long userId) {
        return channelRepo.findChannelsByUserId(userId);
    }
    }

    

// @Service
// public class ChatService {

//     private final MessagessRepo messagessRepo;
//     private final SimpMessagingTemplate simpMessagingTemplate;
//     private  final ChannelRepo channelRepo;
//     private final MainJwts mainJwts;
//     private final  UserReposteryes userReposteryes;
//     public ChatService(MessagessRepo messagessRepo,
//                        SimpMessagingTemplate simpMessagingTemplate, ChannelRepo channelRepo,MainJwts mainJwts,UserReposteryes userReposteryes) {
//         this.messagessRepo = messagessRepo;
//         this.simpMessagingTemplate = simpMessagingTemplate;
//         this.channelRepo = channelRepo;
//         this.mainJwts = mainJwts;
//         this.userReposteryes = userReposteryes;
//     }

//     public void sendMessage(Messages messages,String access) {
//         Long userId = mainJwts.getUserIdFromAccessToken(access);
//         User user = userReposteryes.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//         String role = user.getRole();
//         if(role.equals("TEACHER")){
//        MessgessssK entity = new MessgessssK();
//         entity.setSender(messages.getSender());
//         entity.setContent(messages.getContent());
//         entity.setNow(LocalTime.now());

//         Channels channel = channelRepo.findById(messages.getChannelId()).orElseThrow(() -> new RuntimeException("Channel not found"));


//         entity.setChannel(channel);

//         messagessRepo.save(entity);
//         // ارساله رساله للقناه الي دز عليها
//         simpMessagingTemplate.convertAndSend(
//                 "/topic/channel." + messages.getChannelId(),
//                 messages
//         );
//         }else{
//             throw new RuntimeException("You are not allowed to send messages");
//         }
 
//     }
//     public boolean joinChannel(Long channelId, Long userId)  {
//         try{
//             User user = userReposteryes.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

//             Long classId = user.getClassId();
//             String classABC = user.getClassABC();

            
        
//         Channels channel = channelRepo.findById(channelId).orElseThrow(() -> new RuntimeException("Channel not found"));
//         Long channelClassId = channel.getClassiD();
//         String channelClassABC = channel.getClassABC();
//         if(classId.longValue() != channelClassId.longValue() || !classABC.equals(channelClassABC)){
//             return false;
//         }


//         channelRepo.joinChannel(channelId, userId);
//         return true;
//         } catch (Exception e){
//             return false;
//         }}
//     public List<Channels> getUserChannels(Long userId){
//         // return channelRepo.findAll().stream().filter(channel -> channel.getUsers().stream().anyMatch(user -> user.getId().equals(userId))).collect(Collectors.toList());
//         // تكدرون تستخدمون هذهي بي مشارريع صقيره بس حقيقيه ميصير لانو يجيب جميع قنوات مثلا عندك الف قناه اذا راح يجيب كلشي عنهم راح يطير سيرفرك
//     }
       

// }}
