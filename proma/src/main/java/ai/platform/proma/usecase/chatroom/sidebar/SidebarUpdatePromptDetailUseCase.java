package ai.platform.proma.usecase.chatroom.sidebar;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.PromptDetailUpdateRequestDto;

@UseCase
public interface SidebarUpdatePromptDetailUseCase {
    Boolean updatePromptDetail(PromptDetailUpdateRequestDto promptDetailUpdateRequestDto, Long promptId, Long userId);
}
