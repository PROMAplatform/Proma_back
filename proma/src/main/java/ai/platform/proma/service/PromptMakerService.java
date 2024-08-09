package ai.platform.proma.service;

import ai.platform.proma.domain.*;
import ai.platform.proma.domain.enums.PromptMethod;
import ai.platform.proma.dto.request.BlockSaveRequestDto;
import ai.platform.proma.dto.request.ListPromptAtom;
import ai.platform.proma.dto.request.PromptSaveRequestDto;
import ai.platform.proma.dto.response.SelectBlockDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repositroy.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PromptMakerService {
    private final BlockRepository blockRepository;
    private final UserRepository userRepository;
    private final PromptRepository promptRepository;
    private final CommunicationMethodRepository communicationMethodRepository;
    private final PromptBlockRepository promptBlockRepository;

    public Boolean makeBlock(BlockSaveRequestDto blockSaveRequestDto, User user) {

        PromptMethods promptMethods = communicationMethodRepository.findByPromptMethod(PromptMethod.fromValue(blockSaveRequestDto.getPromptMethod()))
                .orElseThrow(() -> new ApiException(ErrorDefine.COMMUNICATION_METHOD_NOT_FOUND));
        Block saveBlock = blockSaveRequestDto.toEntity(promptMethods, user, blockSaveRequestDto);
        blockRepository.save(saveBlock);

        return true;
    }

    public Map<String, List<SelectBlockDto>> searchBlock(User user, String promptMethod) {

        PromptMethods promptMethods = communicationMethodRepository.findByPromptMethod(PromptMethod.fromValue(promptMethod))
                .orElseThrow(() -> new ApiException(ErrorDefine.COMMUNICATION_METHOD_NOT_FOUND));

        List<Block> blocks = blockRepository.findByUserOrUserIsNullAndPromptMethods(user, promptMethods);
        Map<String, List<SelectBlockDto>> blockMap = new HashMap<>();

        blockMap.put("selectBlock", blocks.stream()
                .map(SelectBlockDto::of)
                .toList());

        return blockMap;
    }


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
