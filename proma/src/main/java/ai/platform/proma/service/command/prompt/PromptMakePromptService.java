package ai.platform.proma.service.command.prompt;

import ai.platform.proma.domain.*;
import ai.platform.proma.domain.enums.BlockCategory;
import ai.platform.proma.domain.enums.PromptMethod;
import ai.platform.proma.dto.request.AiListPromptAtom;
import ai.platform.proma.dto.request.ListPromptAtom;
import ai.platform.proma.dto.request.PromptSaveRequestDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.*;
import ai.platform.proma.usecase.prompt.PromptMakePromptUseCase;
import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PromptMakePromptService implements PromptMakePromptUseCase {

    private final BlockRepository blockRepository;
    private final PromptRepository promptRepository;
    private final CommunicationMethodRepository communicationMethodRepository;
    private final PromptBlockRepository promptBlockRepository;
    private final UserRepository userRepository;

    public Boolean makePrompt(PromptSaveRequestDto promptSaveRequestDto, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        PromptMethods promptMethods = communicationMethodRepository.findByPromptMethod(PromptMethod.fromValue(promptSaveRequestDto.promptMethod()))
                .orElseThrow(() -> new ApiException(ErrorDefine.COMMUNICATION_METHOD_NOT_FOUND));

        Prompt savePrompt = promptSaveRequestDto.toEntity(user, promptMethods);
        promptRepository.save(savePrompt);

        List<Block> blocks = new ArrayList<>(promptSaveRequestDto.listPromptAtom().stream()
                .map(listPromptAtom -> blockRepository.findByIdAndPromptMethods(listPromptAtom.blockId(), promptMethods)
                        .orElseThrow(() -> new ApiException(ErrorDefine.BLOCK_NOT_FOUND)))
                .toList());

        if (promptSaveRequestDto.aiListPromptAtom() != null) {
            List<Block> savedBlocks = promptSaveRequestDto.aiListPromptAtom().stream()
                    .map(aiListPromptAtom -> saveBlock(aiListPromptAtom, promptMethods, user))
                    .toList();

            blocks.addAll(savedBlocks);
        }

        List<PromptBlock> savePromptBlocks = blocks.stream()
                .map(block -> ListPromptAtom.toEntity(savePrompt, block))
                .toList();

        promptBlockRepository.saveAll(savePromptBlocks);

        return true;
    }

    private Block saveBlock(AiListPromptAtom aiListPromptAtom, PromptMethods promptMethods, User user ) {
        return blockRepository.save(Block.builder()
                .user(user)
                .blockValue(aiListPromptAtom.blockValue())
                .blockDescription(aiListPromptAtom.blockDescription())
                .blockCategory(BlockCategory.fromValue(aiListPromptAtom.blockCategory()))
                .promptMethods(promptMethods)
                .build());
    }
}
