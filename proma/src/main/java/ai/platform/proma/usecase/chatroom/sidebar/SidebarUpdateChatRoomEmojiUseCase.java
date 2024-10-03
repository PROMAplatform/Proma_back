package ai.platform.proma.usecase.chatroom.sidebar;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.UpdateEmojiRequestDto;
import ai.platform.proma.dto.response.ChatRoomIdResponseDto;

@UseCase
public interface SidebarUpdateChatRoomEmojiUseCase {
    ChatRoomIdResponseDto updateChatRoomEmoji(Long chatRoomId, UpdateEmojiRequestDto chatRoomUpdateEmojiRequestDto, User user);
}
