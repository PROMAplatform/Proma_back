package ai.platform.proma.usecase.chatroom.sidebar;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;

@UseCase
public interface SidebarDeletePromptUseCase {
    Boolean deletePrompt(Long promptId, User user);
}
