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
import io.micrometer.observation.transport.SenderContext;
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
        MultipartFile voiceFile = messages.getVoice();
        if (voiceFile != null && !voiceFile.isEmpty()) {
            String folder = "uploads/voices/";
            Path path = Paths.get(folder + voiceFile.getOriginalFilename());
            Files.createDirectories(path.getParent());
            Files.write(path, voiceFile.getBytes());
            entity.setVoice(voiceFile.getBytes());
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
    public List<MessgessssK> getChannelMessages(Long channelId) {
        Channels channel = channelRepo.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        return channel.getMessgessssKs();
    }
    public List<Channels> getAllChannels() {
        return channelRepo.findAll();   
    }
    
    public Optional<Channels> getChannelByName(String name) {
        return channelRepo.findByName(name);
    }
    public Optional<Channels> getChannelByClassId(Long classId) {
        return channelRepo.findByClass(classId);
    }
    public Optional<Channels> getChannelByClassABC(String classABC) {
        return channelRepo.findByClassABC(classABC);}
    public Optional<Channels> getChannelByUsers(List<User> users) {
        return channelRepo.findByUsers(users);}
    public Long getNumberofUserInChannel(Long channelId){
        Channels channel = channelRepo.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        return (long) channel.getUsers().size();
    }
    public boolean isUserActive(Long userId){
        User user = userReposteryes.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(true);
        userReposteryes.save(user);
        return true;
    }
    public boolean unsetUserActive(Long userId){
        User user = userReposteryes.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(false);
        userReposteryes.save(user);
        return true;
    }
    public List<User> getActiveUsersInChannel(Long channelId) {
        Channels channel = channelRepo.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));  
        List<User> channeList = channel.getUsers(); 
        return channel.getUsers().stream()
        		
                .filter(User::getActive)
                .toList();  
    }
    public List<User> getAllUsersInChannel(Long channelId) {
        Channels channel = channelRepo.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        return channel.getUsers();}
    public List<Channels> getChannelsByUserId(Long userId) {
        return channelRepo.findByUsers_Id(userId); }
    public List<MessgessssK> filterMessagesChannels(Long channelId,String keyword,Long userId){
        Channels channel = channelRepo.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        if(!joinChannel(channelId, userId)){
            throw new RuntimeException("You are not a member of this channel");
        }
        if(keyword == null || keyword.isEmpty()){
            return channel.getMessgessssKs();
        }
        if(joinChannel(channelId, userId)){
            List<User> users = channel.getUsers();
            for(User user: users){
                if(user.getId().equals(userId)){
                    return channel.getMessgessssKs().stream()
                    .filter(Message -> Message.getContent().contains(keyword))
                    .toList();
                }
            }

        }else{
            throw new RuntimeException("You are not a member of this channel");
        }
        return null;


        
    }
    @Transactional(readOnly = true)
    public List<MessgessssK> filterMessagesBySender(Long channelId,String sender,Long userId){
        Channels channel = channelRepo.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        if(!joinChannel(channelId, userId)){
            throw new RuntimeException("You are not a member of this channel");}
        if(sender == null || sender.isEmpty()){
            return channel.getMessgessssKs();   }
        if(joinChannel(channelId, userId)){
            List<User> users = channel.getUsers();
            for(User user: users){
                if(user.getId().equals(userId)){
                    return channel.getMessgessssKs().stream()
                    .filter(Message -> Message.getSender().equals(sender))
                    .toList();      
                }}
     ;
    }else{
    throw new RuntimeException("You are not a member of this channel");};

    return null;






    
}



public List<MessgessssK> filterMessagesByTime(Long channelId, LocalTime from, LocalTime to, Long userId) {
    Channels channel = channelRepo.findById(channelId)
            .orElseThrow(() -> new RuntimeException("Channel not found"));
    if (!joinChannel(channelId, userId)) {
        throw new RuntimeException("You are not a member of this channel");
    }
    if (from == null || to == null) {
        return channel.getMessgessssKs();
    }
    if (joinChannel(channelId, userId)) {
        List<User> users = channel.getUsers();
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return channel.getMessgessssKs().stream()
                        .filter(message -> message.getNow().isAfter(from) && message.getNow().isBefore(to))
                        .toList();
            }
        }
    } else {
        throw new RuntimeException("You are not a member of this channel");
    }
    return null;
}
public List<MessgessssK> filterMessagesByContentAndSender(Long channelId, String keyword, String sender, Long userId) {
    Channels channel = channelRepo.findById(channelId)
            .orElseThrow(() -> new RuntimeException("Channel not found"));
    if (!joinChannel(channelId, userId)) {
        throw new RuntimeException("You are not a member of this channel");}
    if ((keyword == null || keyword.isEmpty()) && (sender == null || sender.isEmpty())) {
        return channel.getMessgessssKs();   
    }
    if (joinChannel(channelId, userId)) {
        List<User> users = channel.getUsers();
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return channel.getMessgessssKs().stream()
                        .filter(message -> message.getContent().contains(keyword) && message.getSender().equals(sender))
                        .toList();      
            }
        }
    } else {
        throw new RuntimeException("You are not a member of this channel");
    }
    return null;


}
public List<MessgessssK> filterMessagesByContentAndTime(Long channelId, String keyword, LocalTime from, LocalTime to, Long userId) {
    Channels channel = channelRepo.findById(channelId)
            .orElseThrow(() -> new RuntimeException("Channel not found"));
    if (!joinChannel(channelId, userId)) {
        throw new RuntimeException("You are not a member of this channel");
    }
    if ((keyword == null || keyword.isEmpty()) && (from == null || to == null)) {
        return channel.getMessgessssKs();   
    }
    if (joinChannel(channelId, userId)) {
        List<User> users = channel.getUsers();
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return channel.getMessgessssKs().stream()
                        .filter(message -> message.getContent().contains(keyword) && message.getNow().isAfter(from) && message.getNow().isBefore(to))
                        .toList();      
            }
        }
    } else {
        throw new RuntimeException("You are not a member of this channel");
    }
    return null;
}
public List<MessgessssK> filterMessagesBySenderAndTime(Long channelId, String sender, LocalTime from, LocalTime to, Long userId) {
    Channels channel = channelRepo.findById(channelId)
            .orElseThrow(() -> new RuntimeException("Channel not found"));
    if (!joinChannel(channelId, userId)) {
        throw new RuntimeException("You are not a member of this channel");}
    if ((sender == null || sender.isEmpty()) && (from == null || to == null)) {
        return channel.getMessgessssKs();   
    }
    if (joinChannel(channelId, userId)) {
        List<User> users = channel.getUsers();
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return channel.getMessgessssKs().stream()
                        .filter(message -> message.getSender().equals(sender) && message.getNow().isAfter(from) && message.getNow().isBefore(to))
                        .toList();      
            }
        }
    } else {
        throw new RuntimeException("You are not a member of this channel");
    }
    return null;
}
public List<MessgessssK> filterMessagesByContentAndSenderAndTime(Long channelId, String keyword, String sender, LocalTime from, LocalTime to, Long userId) {
    Channels channel = channelRepo.findById(channelId)
            .orElseThrow(() -> new RuntimeException("Channel not found"));
    if (!joinChannel(channelId, userId)) {
        throw new RuntimeException("You are not a member of this channel");}     
    if ((keyword == null || keyword.isEmpty()) && (sender == null || sender.isEmpty()) && (from == null || to == null)) {
        return channel.getMessgessssKs();}
    if (joinChannel(channelId, userId)) {
        List<User> users = channel.getUsers();
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return channel.getMessgessssKs().stream()
                        .filter(message -> message.getContent().contains(keyword) && message.getSender().equals(sender) && message.getNow().isAfter(from) && message.getNow().isBefore(to))
                        .toList();      
            }
        }
    } else {
        throw new RuntimeException("You are not a member of this channel");
    }
    return null;
}
@Transactional(readOnly = true)
public List<MessgessssK> getAllMessagesInChannel(Long channelId, Long userId) {
    Channels channel = channelRepo.findById(channelId)
            .orElseThrow(() -> new RuntimeException("Channel not found"));
    if (!joinChannel(channelId, userId)) {
        throw new RuntimeException("You are not a member of this channel");
    }
    return channel.getMessgessssKs();
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
