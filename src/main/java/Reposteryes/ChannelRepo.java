package Reposteryes;

import model.Channels;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import model.User;



@Repository
public interface ChannelRepo extends JpaRepository<Channels,Long> {
    // void save(Channels channel);
  @Query("""
        SELECT c
        FROM Channels c
        JOIN c.users u
        WHERE u.id = :userId
    """)
    List<Channels> findChannelsByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = """
        INSERT INTO user_channels (channel_id, user_id)
        VALUES (:channelId, :userId)
    """, nativeQuery = true)
    void joinChannel(Long channelId, Long userId);
  
    // void joinChannel(Long channelId, Long userId);
    Optional<Channels> findByName(String name);
    Optional<Channels>  findByClass(Long classId);
    Optional<Channels>  findByClassABC(String classABC);
    Optional<Channels>  findByUsers(List<User> users);
}
