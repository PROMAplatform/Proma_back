package ai.platform.proma.service.command.chatroom.sidebar;

import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.User;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.dto.request.PromptDetailUpdateRequestDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.PromptRepository;
import ai.platform.proma.usecase.chatroom.sidebar.SidebarUpdatePromptDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SidebarUpdatePromptDetailService implements SidebarUpdatePromptDetailUseCase {

    private final PromptRepository promptRepository;
    public Boolean updatePromptDetail(PromptDetailUpdateRequestDto promptDetailUpdateRequestDto, Long promptId, User user) {
        Prompt prompt = promptRepository.findByIdAndUser(promptId,user)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));

        prompt.updatePromptDetail(promptDetailUpdateRequestDto.getPromptTitle(), promptDetailUpdateRequestDto.getPromptDescription(), PromptCategory.fromValue(promptDetailUpdateRequestDto.getPromptCategory()), prompt.getPromptPreview());

        return true;
    }
}
