package ai.platform.proma.repositroy;

import ai.platform.proma.domain.User;
import ai.platform.proma.security.UserLoginForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<UserLoginForm> findBySocialIdAndRefreshToken(String username);

    Optional<UserLoginForm> findBySocialIdAndRefreshToken(String userId, String refreshToken);

    Optional<User> findBySocialId(String socialId);
}

