package ai.platform.proma.service;

import ai.platform.proma.domain.*;
import ai.platform.proma.dto.request.ListPromptAtom;
import ai.platform.proma.dto.request.PromptUpdateRequestDto;
import ai.platform.proma.dto.response.MessageListResponseDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final PromptRepository promptRepository;
    private final BlockRepository blockRepository;
    private final PromptBlockRepository promptBlockRepository;

    public Map<String, List<MessageListResponseDto>> enterChatRoom(Long chatRoomId, User user) {

        ChatRoom chatRoom = chatRoomRepository.findByIdAndUser(chatRoomId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.CHAT_ROOM_NOT_FOUND));

        List<Message> messages = messageRepository.findByChatRoom(chatRoom);
        if (messages.isEmpty()) {
            throw new ApiException(ErrorDefine.MESSAGE_NOT_FOUND);
        }
        Map<String, List<MessageListResponseDto>> response = new HashMap<>();

        response.put("selectChatting", messages.stream()
                .map(message -> MessageListResponseDto.of(message, message.getChatRoom()))
                .collect(Collectors.toList()));

        return response;
    }

    public Boolean updatePromptBlock(PromptUpdateRequestDto promptUpdateRequestDto, Long promptId, User user) {
        Prompt prompt = promptRepository.findByIdAndUser(promptId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));
        prompt.updatePreview(promptUpdateRequestDto.getPromptPreview());

        List<PromptBlock> promptBlock = promptBlockRepository.findByPrompt(prompt);
        promptBlockRepository.deleteAll(promptBlock);

        List<Block> blocks = blockRepository.findAllById(promptUpdateRequestDto.getListPromptAtom().stream()
                .map(ListPromptAtom::getBlockId)
                .collect(Collectors.toList()));

        List<PromptBlock> savePromptBlocks = blocks.stream()
                .map(block -> ListPromptAtom.toEntity(prompt, block))
                .toList();

        promptBlockRepository.saveAll(savePromptBlocks);

        return true;
    }
}
