package ai.platform.proma.service.command.prompt;

import ai.platform.proma.domain.Block;
import ai.platform.proma.domain.User;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.BlockRepository;
import ai.platform.proma.repository.UserRepository;
import ai.platform.proma.usecase.prompt.PromptDeleteBlockUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PromptDeleteBlockService implements PromptDeleteBlockUseCase {

    private final BlockRepository blockRepository;
    private final UserRepository userRepository;

    public Boolean deleteBlock(Long blockId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Block block = blockRepository.findByIdAndUser(blockId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.BLOCK_NOT_FOUND));
        if(block.getUser() == null)
            throw new ApiException(ErrorDefine.BLOCK_NOT_DELETE);
        block.updateBlock();
        return true;
    }
}
