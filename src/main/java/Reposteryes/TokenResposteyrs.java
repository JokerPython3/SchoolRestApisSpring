package Reposteryes;

import model.RefreshTokens;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TokenResposteyrs extends JpaRepository<RefreshTokens,Long> {
    Optional<RefreshTokens> findByToken(String token);
    Optional<RefreshTokens> findByUserUsername(Optional<User> user);
    void deleteByUser(User user);
}
