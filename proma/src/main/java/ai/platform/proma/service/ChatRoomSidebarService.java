package ai.platform.proma.service;

import ai.platform.proma.domain.*;
import ai.platform.proma.dto.request.ChatRoomSaveRequestDto;
import ai.platform.proma.dto.request.ChatRoomUpdateEmojiRequestDto;
import ai.platform.proma.dto.request.PromptDetailUpdateRequestDto;
import ai.platform.proma.dto.response.ChatRoomListResponseDto;
import ai.platform.proma.dto.response.ChatRoomIdResponseDto;
import ai.platform.proma.dto.response.PromptListResponseDto;
import ai.platform.proma.dto.response.SelectBlockDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repositroy.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class ChatRoomSidebarService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final PromptRepository promptRepository;
    private final CommunicationMethodRepository communicationMethodRepository;
    private final BlockRepository blockRepository;
    private final PromptBlockRepository promptBlockRepository;
    public ChatRoomIdResponseDto saveChatRoom(ChatRoomSaveRequestDto chatRoomSaveRequestDto, Long userId) {
        User user = userRepository.findById(userId)
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

    public ChatRoomIdResponseDto updateEmoji(Long chatRoomId, ChatRoomUpdateEmojiRequestDto chatRoomUpdateEmojiRequestDto, Long userId) {
        User user = userRepository.findById(userId)
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

    public Boolean updatePromptDetail(PromptDetailUpdateRequestDto promptDetailUpdateRequestDto, Long promptId, Long userId) {
        User user = userRepository.findById(userId)
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

    public Map<String, List<PromptListResponseDto>> getPromptList(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        List<Prompt> prompts = promptRepository.findByUser(user);
        Optional<PromptBlock> promptBlocks = null;

        prompts.stream()
                .flatMap(prompt -> prompt.getPromptBlocks().stream())
                .forEach(promptBlock -> {
                    Optional<PromptBlock> optionalBlock = promptBlockRepository.findById(promptBlock.getId());
                    optionalBlock.ifPresent(block -> {
                        System.err.println(block.getBlock().getBlockValue());
                    });
                });

        Map<String, List<PromptListResponseDto>> promptMap = new HashMap<>();
        Map<String, SelectBlockDto> selectBlockDtoMap = new HashMap<>();
        List<SelectBlockDto> selectBlockDtos = new ArrayList<>();
        System.err.println("여기 전");

        promptMap.put("selectPrompt", prompts.stream()
                .map(prompt -> PromptListResponseDto.of(
                        prompt,
                        prompt.getPromptMethods(),
                        prompt.getPromptBlocks().stream()
                                .map(promptBlock -> SelectBlockDto.of(promptBlock.getBlock())).collect(Collectors.toList())))
                .collect(Collectors.toList()));

        return promptMap;
    }
}
