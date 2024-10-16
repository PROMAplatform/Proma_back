package ai.platform.proma.service.query.chatroom.sidebar;

import ai.platform.proma.domain.ChatRoom;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.response.ChatRoomListResponseDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.ChatRoomRepository;
import ai.platform.proma.repository.UserRepository;
import ai.platform.proma.usecase.chatroom.sidebar.SidebarGetChatRoomListUseCase;
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
public class SidebarGetChatRoomListService implements SidebarGetChatRoomListUseCase {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    public Map<String, List<ChatRoomListResponseDto>> getChatRoomList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        List<ChatRoom> chatRooms = chatRoomRepository.findByUser(user);

        Map<String, List<ChatRoomListResponseDto>> chatRoomMap = new HashMap<>();

        chatRoomMap.put("selectChatroom", chatRooms.stream()
                .map(ChatRoomListResponseDto::of)
                .collect(Collectors.toList()));


        return chatRoomMap;
    }
}
