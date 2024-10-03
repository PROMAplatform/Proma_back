package ai.platform.proma.service.query.prompt;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.PromptMethod;
import ai.platform.proma.dto.response.SelectBlockDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.BlockRepository;
import ai.platform.proma.repository.CommunicationMethodRepository;
import ai.platform.proma.usecase.prompt.PromptSearchBlockUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromptSearchBlockService implements PromptSearchBlockUseCase {

    private final CommunicationMethodRepository communicationMethodRepository;
    private final BlockRepository blockRepository;


    public Map<String, List<SelectBlockDto>> searchBlock(User user, String promptMethod) {

        PromptMethods promptMethods = communicationMethodRepository.findByPromptMethod(PromptMethod.fromValue(promptMethod))
                .orElseThrow(() -> new ApiException(ErrorDefine.COMMUNICATION_METHOD_NOT_FOUND));

        List<Block> blocks = blockRepository.findByUserOrUserIsNullAndPromptMethodsAndOnGoingIsTrue(user, promptMethods);
        Map<String, List<SelectBlockDto>> blockMap = new HashMap<>();
        List<SelectBlockDto> blockDtos = new ArrayList<>();

        blocks.forEach(block -> {
            if(block.getUser() == null)
                blockDtos.add(SelectBlockDto.ofDefault(block, true));
            else
                blockDtos.add(SelectBlockDto.ofDefault(block, false));
        });
        blockMap.put("selectBlock", blockDtos);

        return blockMap;
    }
}
