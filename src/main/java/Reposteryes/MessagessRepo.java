package Reposteryes;

import model.MessgessssK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MessagessRepo extends JpaRepository<model.MessgessssK,Long> {

    List<MessgessssK> findByChannel_Id(Long id);
    // Optional<MessgessssK> findById(Long id);
    // List<MessgessssK> findByMessageId(Long messageId);
    // @Override
    // default void delete(MessgessssK entity) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'delete'");
    // }

}
