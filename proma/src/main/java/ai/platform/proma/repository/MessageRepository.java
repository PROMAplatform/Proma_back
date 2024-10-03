package ai.platform.proma.repository;

import ai.platform.proma.domain.ChatRoom;
import ai.platform.proma.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByChatRoom(ChatRoom chatRoom);

    List<Message> findAllByPromptId(Long promptId);
}
