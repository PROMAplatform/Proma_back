package ai.platform.proma.usecase.chatroom.sidebar;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.response.ChatRoomListResponseDto;

import java.util.List;
import java.util.Map;

@UseCase
public interface SidebarGetChatRoomListUseCase {
    Map<String, List<ChatRoomListResponseDto>> getChatRoomList(User user);
}
