package ai.platform.proma.service.command.chatroom.sidebar;

import ai.platform.proma.domain.ChatRoom;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.ChatRoomSaveRequestDto;
import ai.platform.proma.dto.response.ChatRoomIdResponseDto;
import ai.platform.proma.dto.response.ChatRoomListResponseDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.ChatRoomRepository;
import ai.platform.proma.repository.UserRepository;
import ai.platform.proma.usecase.chatroom.sidebar.SidebarSaveChatRoomUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SidebarSaveChatRoomService implements SidebarSaveChatRoomUseCase {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public ChatRoomIdResponseDto saveChatRoom(ChatRoomSaveRequestDto chatRoomSaveRequestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomSaveRequestDto.toEntity(user);
        chatRoomRepository.save(chatRoom);

        return ChatRoomIdResponseDto.of(chatRoom.getId());
    }

}
