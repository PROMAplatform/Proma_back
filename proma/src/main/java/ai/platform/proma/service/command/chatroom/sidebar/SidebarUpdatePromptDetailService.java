package ai.platform.proma.service.command.chatroom.sidebar;

import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.request.PromptDetailUpdateRequestDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.PromptRepository;
import ai.platform.proma.repository.UserRepository;
import ai.platform.proma.usecase.chatroom.sidebar.SidebarUpdatePromptDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SidebarUpdatePromptDetailService implements SidebarUpdatePromptDetailUseCase {

    private final PromptRepository promptRepository;
    private final UserRepository userRepository;
    public Boolean updatePromptDetail(PromptDetailUpdateRequestDto promptDetailUpdateRequestDto, Long promptId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Prompt prompt = promptRepository.findByIdAndUser(promptId,user)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));

        prompt.updatePromptDetail(promptDetailUpdateRequestDto.promptTitle(), promptDetailUpdateRequestDto.promptDescription(), PromptCategory.fromValue(promptDetailUpdateRequestDto.promptCategory()), prompt.getPromptPreview());

        return true;
    }
}
