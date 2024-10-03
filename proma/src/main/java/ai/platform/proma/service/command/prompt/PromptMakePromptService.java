package ai.platform.proma.service.command.prompt;

import ai.platform.proma.domain.*;
import ai.platform.proma.domain.enums.PromptMethod;
import ai.platform.proma.dto.request.ListPromptAtom;
import ai.platform.proma.dto.request.PromptSaveRequestDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.BlockRepository;
import ai.platform.proma.repository.CommunicationMethodRepository;
import ai.platform.proma.repository.PromptBlockRepository;
import ai.platform.proma.repository.PromptRepository;
import ai.platform.proma.usecase.prompt.PromptMakePromptUseCase;
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
    public Boolean makePrompt(PromptSaveRequestDto promptSaveRequestDto, User user) {

        PromptMethods promptMethods = communicationMethodRepository.findByPromptMethod(PromptMethod.fromValue(promptSaveRequestDto.getPromptMethod()))
                .orElseThrow(() -> new ApiException(ErrorDefine.COMMUNICATION_METHOD_NOT_FOUND));

        Prompt savePrompt = promptSaveRequestDto.toEntity(user, promptMethods, promptSaveRequestDto);
        promptRepository.save(savePrompt);

        List<Block> blocks = promptSaveRequestDto.getListPromptAtom().stream()
                .map(listPromptAtom -> blockRepository.findByIdAndPromptMethods(listPromptAtom.getBlockId(), promptMethods)
                        .orElseThrow(() -> new ApiException(ErrorDefine.BLOCK_NOT_FOUND)))
                .toList();

        List<PromptBlock> savePromptBlocks = blocks.stream()
                .map(block -> ListPromptAtom.toEntity(savePrompt, block))
                .toList();

        promptBlockRepository.saveAll(savePromptBlocks);

        return true;
    }
}
