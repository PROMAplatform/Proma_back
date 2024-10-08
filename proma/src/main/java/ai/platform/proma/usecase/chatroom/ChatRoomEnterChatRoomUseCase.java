package ai.platform.proma.usecase.chatroom;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.response.MessageListResponseDto;

import java.util.List;
import java.util.Map;

@UseCase
public interface ChatRoomEnterChatRoomUseCase {
    Map<String, List<MessageListResponseDto>> enterChatRoom(Long chatRoomId, Long userId);
}
