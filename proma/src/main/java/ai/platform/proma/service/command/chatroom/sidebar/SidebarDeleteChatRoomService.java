package ai.platform.proma.service.command.chatroom.sidebar;

import ai.platform.proma.domain.ChatRoom;
import ai.platform.proma.domain.User;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.ChatRoomRepository;
import ai.platform.proma.usecase.chatroom.sidebar.SidebarDeleteChatRoomUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SidebarDeleteChatRoomService implements SidebarDeleteChatRoomUseCase {

    private final ChatRoomRepository chatRoomRepository;
    public Boolean deleteChatRoom(Long chatRoomId, User user) {
        ChatRoom chatRoom = chatRoomRepository.findByIdAndUser(chatRoomId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.CHAT_ROOM_NOT_FOUND));

        chatRoomRepository.delete(chatRoom);

        return true;
    }
}
