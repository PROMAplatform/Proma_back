package ai.platform.proma.repositroy;

import ai.platform.proma.domain.User;
import ai.platform.proma.security.UserLoginForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT b.socialId as id, b.role AS role FROM User b WHERE b.socialId = :socialId AND  b.refreshToken is not null")
    Optional<UserLoginForm> findBySocialIdAndRefreshToken(@Param("socialId") String socialId);

    @Query("SELECT b.socialId as id, b.role AS role FROM User b WHERE b.socialId = :socialId AND b.refreshToken = :refreshToken")
    Optional<UserLoginForm> findBySocialIdAndRefreshToken(@Param("socialId") String socialId, @Param("refreshToken") String refreshToken);

    Optional<User> findBySocialId(String socialId);
}

