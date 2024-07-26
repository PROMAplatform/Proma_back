package ai.platform.proma.repositroy;

import ai.platform.proma.domain.ChatRoom;
import ai.platform.proma.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByUser(User user);

    Optional<ChatRoom> findByIdAndUser(Long chatRoomId, User user);
}
