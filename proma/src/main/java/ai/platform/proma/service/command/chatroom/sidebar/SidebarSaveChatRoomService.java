package ai.platform.proma.service.command.chatroom.sidebar;

import ai.platform.proma.domain.ChatRoom;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.ChatRoomSaveRequestDto;
import ai.platform.proma.dto.response.ChatRoomIdResponseDto;
import ai.platform.proma.dto.response.ChatRoomListResponseDto;
import ai.platform.proma.repository.ChatRoomRepository;
import ai.platform.proma.usecase.chatroom.sidebar.SidebarSaveChatRoomUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SidebarSaveChatRoomService implements SidebarSaveChatRoomUseCase {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomIdResponseDto saveChatRoom(ChatRoomSaveRequestDto chatRoomSaveRequestDto, User user) {
        ChatRoom chatRoom = chatRoomSaveRequestDto.toEntity(chatRoomSaveRequestDto, user);
        chatRoomRepository.save(chatRoom);

        return ChatRoomIdResponseDto.of(chatRoom.getId());
    }

}
