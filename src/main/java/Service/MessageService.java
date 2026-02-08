package Service;


import Reposteryes.MessagessRepo;
import model.MessgessssK;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import JwtsManager.MainJwts;

import java.util.List;

@Service
public class MessageService {

    private final MessagessRepo repo;
    // ميحتاج فلتر راح يفتش توكن
    public MessageService(MessagessRepo repo) {
        this.repo = repo;
    }
// يجيب رسايل قناه عن طريق ايدي بعدين سويه يجيب رسايل بس 50 حتى مينبعص
    @Transactional(readOnly = true)
    public List<MessgessssK> getHistory(Long channelId) {
        return repo.findByChannel_Id(channelId);
    }
}
