package Reposteryes;

import model.MessgessssK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessagessRepo extends JpaRepository<model.MessgessssK,Long> {

    List<MessgessssK> findByChannel_Id(Long id);

}
