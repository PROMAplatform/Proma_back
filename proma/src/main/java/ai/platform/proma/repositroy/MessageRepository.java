package ai.platform.proma.repositroy;

import ai.platform.proma.domain.ChatRoom;
import ai.platform.proma.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByChatRoom(ChatRoom chatRoom);

    List<Message> findAllByPromptId(Long promptId);
}
