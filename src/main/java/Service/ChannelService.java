package Service;

import DTO.Messages;
import Reposteryes.ChannelRepo;
import Reposteryes.MessagessRepo;
import model.Channels;
import model.MessgessssK;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
public class ChannelService {
    private  final ChannelRepo channelRepo;
    private  final SimpMessagingTemplate simpMessagingTemplate;
    private final MessagessRepo messagessRepo;

    public ChannelService(ChannelRepo channelRepo, SimpMessagingTemplate simpMessagingTemplate,MessagessRepo messagessRepo) {
        this.channelRepo = channelRepo;
        this.simpMessagingTemplate = simpMessagingTemplate;this.messagessRepo = messagessRepo;}
    public Channels createChannel(Channels channel){
        Channels savedChannel =  channelRepo.save(channel);
        simpMessagingTemplate.convertAndSend("/topic/channels", savedChannel);
        return  savedChannel;
        // يعمل قناه عن طريق جدول channel اي انشاء قنوات يدوي من قاعده بيانات امن احسه ال ايدي قناه هو نفسه جدول id بي جدول قناه
    }
    // public Messages sendMessage(Messages message){
    //     Channels channels = channelRepo.findById(message.getChannelId()).orElseThrow(() -> new RuntimeException("Channel not found"));
    //     MessgessssK messgessssK = new MessgessssK();
    //     messgessssK.setChannel(channels);
    //     messgessssK.setContent(message.getContent());
    //     messgessssK.setSender(message.getSender());
    //     MessgessssK messgessssK1 = messagessRepo.save(messgessssK);


    //     simpMessagingTemplate.convertAndSend("/topic/channel." + message.getChannelId(), message);
    //     return message;
    // }
}
