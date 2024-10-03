package ai.platform.proma.usecase.chatroom;

import ai.platform.proma.annotation.UseCase;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.PromptUpdateRequestDto;

@UseCase
public interface ChatRoomUpdatePromptBlockUseCase {
    Boolean updatePromptBlock(PromptUpdateRequestDto promptUpdateRequestDto, Long promptId, User user);
}
