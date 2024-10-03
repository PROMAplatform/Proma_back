package ai.platform.proma.usecase.chatroom.sidebar;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.response.PromptListResponseDto;

import java.util.List;
import java.util.Map;

@UseCase
public interface SidebarGetPromptListUseCase {
    Map<String, List<PromptListResponseDto>> getPromptList(User user);
}
