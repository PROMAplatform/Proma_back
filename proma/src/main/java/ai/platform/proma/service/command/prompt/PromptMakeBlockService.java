package ai.platform.proma.service.command.prompt;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.PromptMethod;
import ai.platform.proma.dto.request.BlockSaveRequestDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.BlockRepository;
import ai.platform.proma.repository.CommunicationMethodRepository;
import ai.platform.proma.usecase.prompt.PromptMakeBlockUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PromptMakeBlockService implements PromptMakeBlockUseCase {

    private final CommunicationMethodRepository communicationMethodRepository;
    private final BlockRepository blockRepository;

    public Boolean makeBlock(BlockSaveRequestDto blockSaveRequestDto, User user) {

        PromptMethods promptMethods = communicationMethodRepository.findByPromptMethod(PromptMethod.fromValue(blockSaveRequestDto.getPromptMethod()))
                .orElseThrow(() -> new ApiException(ErrorDefine.COMMUNICATION_METHOD_NOT_FOUND));
        Block saveBlock = blockSaveRequestDto.toEntity(promptMethods, user, blockSaveRequestDto);
        blockRepository.save(saveBlock);

        return true;
    }

}
