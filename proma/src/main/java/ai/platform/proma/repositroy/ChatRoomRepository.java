package ai.platform.proma.repositroy;

import ai.platform.proma.domain.ChatRoom;
import ai.platform.proma.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByUser(User user);

    Optional<ChatRoom> findByIdAndUser(Long chatRoomId, User user);
}
