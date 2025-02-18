package ai.platform.proma.service.command.chatroom;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.PromptBlock;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.ListPromptAtom;
import ai.platform.proma.dto.request.PromptUpdateRequestDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.BlockRepository;
import ai.platform.proma.repository.PromptBlockRepository;
import ai.platform.proma.repository.PromptRepository;
import ai.platform.proma.repository.UserRepository;
import ai.platform.proma.usecase.chatroom.ChatRoomUpdatePromptBlockUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomUpdatePromptBlockService implements ChatRoomUpdatePromptBlockUseCase {

    private final PromptRepository promptRepository;
    private final BlockRepository blockRepository;
    private final PromptBlockRepository promptBlockRepository;
    private final UserRepository userRepository;
    public Boolean updatePromptBlock(PromptUpdateRequestDto promptUpdateRequestDto, Long promptId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Prompt prompt = promptRepository.findByIdAndUser(promptId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));

        prompt.updatePreview(promptUpdateRequestDto.promptPreview());

        List<PromptBlock> promptBlock = promptBlockRepository.findByPrompt(prompt);
        promptBlockRepository.deleteAll(promptBlock);

        List<Block> blocks = blockRepository.findAllById(promptUpdateRequestDto.listPromptAtom().stream()
                .map(ListPromptAtom::blockId)
                .collect(Collectors.toList()));

        List<PromptBlock> savePromptBlocks = blocks.stream()
                .map(block -> ListPromptAtom.toEntity(prompt, block))
                .toList();

        promptBlockRepository.saveAll(savePromptBlocks);

        return true;
    }
}
