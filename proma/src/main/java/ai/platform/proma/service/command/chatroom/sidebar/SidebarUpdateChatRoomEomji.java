package ai.platform.proma.service.command.chatroom.sidebar;

import ai.platform.proma.domain.ChatRoom;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.UpdateEmojiRequestDto;
import ai.platform.proma.dto.response.ChatRoomIdResponseDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.ChatRoomRepository;
import ai.platform.proma.usecase.chatroom.sidebar.SidebarUpdateChatRoomEmojiUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SidebarUpdateChatRoomEomji implements SidebarUpdateChatRoomEmojiUseCase {

    private final ChatRoomRepository chatRoomRepository;
    public ChatRoomIdResponseDto updateChatRoomEmoji(Long chatRoomId, UpdateEmojiRequestDto chatRoomUpdateEmojiRequestDto, User user) {
        ChatRoom chatRoom = chatRoomRepository.findByIdAndUser(chatRoomId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.CHAT_ROOM_NOT_FOUND));

        chatRoom.updateEmoji(chatRoomUpdateEmojiRequestDto.getEmoji());

        return ChatRoomIdResponseDto.of(chatRoom.getId());
    }
}
