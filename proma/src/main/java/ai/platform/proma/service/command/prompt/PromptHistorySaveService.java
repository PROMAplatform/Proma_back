package ai.platform.proma.service.command.prompt;

import ai.platform.proma.domain.BlockHistory;
import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.PromptMethod;
import ai.platform.proma.dto.request.PromptHistorySaveReuqestDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.BlockHistoryRepository;
import ai.platform.proma.repository.UserRepository;
import ai.platform.proma.usecase.prompt.PromptHistorySaveUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PromptHistorySaveService implements PromptHistorySaveUsecase {
    private final UserRepository userRepository;
    private final BlockHistoryRepository blockHistoryRepository;

    public Boolean execute(Long userId, PromptHistorySaveReuqestDto promptHistorySaveReuqestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        BlockHistory blockHistory = BlockHistory.builder()
                .history(promptHistorySaveReuqestDto.promptHistory())
                .promptMethod(PromptMethod.fromValue(promptHistorySaveReuqestDto.promptMethod()))
                .promptCategory(promptHistorySaveReuqestDto.promptCategory())
                .user(user)
                .build();

        blockHistoryRepository.save(blockHistory);

        return true;
    }

}
