package ai.platform.proma.service;

import ai.platform.proma.domain.ChatRoom;
import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.ChatRoomSaveRequestDto;
import ai.platform.proma.dto.request.ChatRoomUpdateEmojiRequestDto;
import ai.platform.proma.dto.request.PromptDetailUpdateRequestDto;
import ai.platform.proma.dto.response.ChatRoomListResponseDto;
import ai.platform.proma.dto.response.ChatRoomIdResponseDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repositroy.ChatRoomRepository;
import ai.platform.proma.repositroy.PromptRepository;
import ai.platform.proma.repositroy.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class ChatRoomSidebarService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final PromptRepository promptRepository;
    public ChatRoomIdResponseDto saveChatRoom(ChatRoomSaveRequestDto chatRoomSaveRequestDto) {
        User user = userRepository.findById(chatRoomSaveRequestDto.getUserId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomSaveRequestDto.toEntity(chatRoomSaveRequestDto, user);
        chatRoomRepository.save(chatRoom);

        return ChatRoomIdResponseDto.of(chatRoom.getId());
    }
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

    public ChatRoomIdResponseDto updateEmoji(Long chatRoomId, ChatRoomUpdateEmojiRequestDto chatRoomUpdateEmojiRequestDto) {
        User user = userRepository.findById(chatRoomUpdateEmojiRequestDto.getUserId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findByIdAndUser(chatRoomId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.CHAT_ROOM_NOT_FOUND));

        chatRoom.updateEmoji(chatRoomUpdateEmojiRequestDto.getEmoji());

        return ChatRoomIdResponseDto.of(chatRoom.getId());
    }

    public Boolean deleteChatRoom(Long chatRoomId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findByIdAndUser(chatRoomId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.CHAT_ROOM_NOT_FOUND));

        chatRoomRepository.delete(chatRoom);

        return true;
    }

    public Boolean updatePromptDetail(PromptDetailUpdateRequestDto promptDetailUpdateRequestDto, Long promptId) {
        User user = userRepository.findById(promptDetailUpdateRequestDto.getUserId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
        Prompt prompt = promptRepository.findByIdAndUser(promptId,user)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));

        prompt.updatePromptDetail(promptDetailUpdateRequestDto.getPromptTitle(), promptDetailUpdateRequestDto.getPromptDescription(), promptDetailUpdateRequestDto.getPromptCategory());

        return true;
    }

    public Boolean deletePrompt(Long promptId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));
        Prompt prompt = promptRepository.findByIdAndUser(promptId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));

        promptRepository.delete(prompt);

        return true;
    }
}
