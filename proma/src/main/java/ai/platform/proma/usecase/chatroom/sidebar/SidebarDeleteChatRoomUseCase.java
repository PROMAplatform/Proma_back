package ai.platform.proma.usecase.chatroom.sidebar;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;

@UseCase
public interface SidebarDeleteChatRoomUseCase {
    Boolean deleteChatRoom(Long chatRoomId, User user);
}
