package ai.platform.proma.service;

import ai.platform.proma.domain.*;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.request.ChatRoomSaveRequestDto;
import ai.platform.proma.dto.request.UpdateEmojiRequestDto;
import ai.platform.proma.dto.request.PromptDetailUpdateRequestDto;
import ai.platform.proma.dto.response.ChatRoomListResponseDto;
import ai.platform.proma.dto.response.ChatRoomIdResponseDto;
import ai.platform.proma.dto.response.PromptListResponseDto;
import ai.platform.proma.dto.response.SelectBlockDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repositroy.*;
import lombok.RequiredArgsConstructor;
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
    private final MessageRepository messageRepository;

    public ChatRoomIdResponseDto saveChatRoom(ChatRoomSaveRequestDto chatRoomSaveRequestDto, User user) {
        ChatRoom chatRoom = chatRoomSaveRequestDto.toEntity(chatRoomSaveRequestDto, user);
        chatRoomRepository.save(chatRoom);

        return ChatRoomIdResponseDto.of(chatRoom.getId());
    }
    public Map<String, List<ChatRoomListResponseDto>> getChatRoomList(User user) {
        List<ChatRoom> chatRooms = chatRoomRepository.findByUser(user);

        Map<String, List<ChatRoomListResponseDto>> chatRoomMap = new HashMap<>();

        chatRoomMap.put("selectChatroom", chatRooms.stream()
                .map(ChatRoomListResponseDto::of)
                .collect(Collectors.toList()));


        return chatRoomMap;
    }

    public ChatRoomIdResponseDto updateChatRoomEmoji(Long chatRoomId, UpdateEmojiRequestDto chatRoomUpdateEmojiRequestDto, User user) {
        ChatRoom chatRoom = chatRoomRepository.findByIdAndUser(chatRoomId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.CHAT_ROOM_NOT_FOUND));

        chatRoom.updateEmoji(chatRoomUpdateEmojiRequestDto.getEmoji());

        return ChatRoomIdResponseDto.of(chatRoom.getId());
    }

    public Boolean deleteChatRoom(Long chatRoomId, User user) {
        ChatRoom chatRoom = chatRoomRepository.findByIdAndUser(chatRoomId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.CHAT_ROOM_NOT_FOUND));

        chatRoomRepository.delete(chatRoom);

        return true;
    }

    public Boolean updatePromptDetail(PromptDetailUpdateRequestDto promptDetailUpdateRequestDto, Long promptId, User user) {
        Prompt prompt = promptRepository.findByIdAndUser(promptId,user)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));

        prompt.updatePromptDetail(promptDetailUpdateRequestDto.getPromptTitle(), promptDetailUpdateRequestDto.getPromptDescription(), PromptCategory.fromValue(promptDetailUpdateRequestDto.getPromptCategory()), prompt.getPromptPreview());

        return true;
    }

    public Boolean deletePrompt(Long promptId, User user) {
        Prompt prompt = promptRepository.findByIdAndUser(promptId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));
        List<Message> messages = messageRepository.findAllByPromptId(promptId);

        messages.forEach(Message::update);

        promptRepository.delete(prompt);

        return true;
    }

    public Map<String, List<PromptListResponseDto>> getPromptList(User user) {
        List<Prompt> prompts = promptRepository.findByUserAndScrap(user);

        Map<String, List<PromptListResponseDto>> promptMap = new HashMap<>();

        promptMap.put("selectPrompt", prompts.stream()
                .map(prompt -> PromptListResponseDto.of(
                        prompt,
                        prompt.getPromptMethods(),
                        prompt.getPromptBlocks().stream()
                                .map(promptBlock -> SelectBlockDto.of(promptBlock.getBlock())).collect(Collectors.toList())))
                .collect(Collectors.toList()));

        return promptMap;
    }

    public ChatRoomIdResponseDto updatePromptEmoji(Long promptId, UpdateEmojiRequestDto updateEmojiRequestDto, User user) {
        Prompt prompt = promptRepository.findByIdAndUser(promptId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));

       prompt.updateEmoji(updateEmojiRequestDto.getEmoji());

        return ChatRoomIdResponseDto.of(prompt.getId());
    }
}
