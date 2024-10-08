package ai.platform.proma.service.query.chatroom;

import ai.platform.proma.domain.ChatRoom;
import ai.platform.proma.domain.Message;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.response.MessageListResponseDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.ChatRoomRepository;
import ai.platform.proma.repository.MessageRepository;
import ai.platform.proma.repository.UserRepository;
import ai.platform.proma.usecase.chatroom.ChatRoomEnterChatRoomUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomEnterChatRoomService implements ChatRoomEnterChatRoomUseCase {

    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public Map<String, List<MessageListResponseDto>> enterChatRoom(Long chatRoomId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findByIdAndUser(chatRoomId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.CHAT_ROOM_NOT_FOUND));

        List<Message> messages = messageRepository.findByChatRoom(chatRoom);
        if (messages.isEmpty()) {
            throw new ApiException(ErrorDefine.MESSAGE_NOT_FOUND);
        }
        Map<String, List<MessageListResponseDto>> response = new HashMap<>();

        response.put("selectChatting", messages.stream()
                .map(message -> MessageListResponseDto.of(message, message.getChatRoom()))
                .collect(Collectors.toList()));

        return response;
    }
}
