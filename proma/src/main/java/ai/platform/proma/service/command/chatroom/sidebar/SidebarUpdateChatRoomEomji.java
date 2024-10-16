package ai.platform.proma.service.command.chatroom.sidebar;

import ai.platform.proma.domain.ChatRoom;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.UpdateEmojiRequestDto;
import ai.platform.proma.dto.response.ChatRoomIdResponseDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.ChatRoomRepository;
import ai.platform.proma.repository.UserRepository;
import ai.platform.proma.usecase.chatroom.sidebar.SidebarUpdateChatRoomEmojiUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SidebarUpdateChatRoomEomji implements SidebarUpdateChatRoomEmojiUseCase {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    public ChatRoomIdResponseDto updateChatRoomEmoji(Long chatRoomId, UpdateEmojiRequestDto chatRoomUpdateEmojiRequestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findByIdAndUser(chatRoomId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.CHAT_ROOM_NOT_FOUND));

        chatRoom.updateEmoji(chatRoomUpdateEmojiRequestDto.emoji());

        return ChatRoomIdResponseDto.of(chatRoom.getId());
    }
}
