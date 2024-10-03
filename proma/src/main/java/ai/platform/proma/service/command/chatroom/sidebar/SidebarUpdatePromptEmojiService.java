package ai.platform.proma.service.command.chatroom.sidebar;

import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.User;
import ai.platform.proma.dto.request.UpdateEmojiRequestDto;
import ai.platform.proma.dto.response.ChatRoomIdResponseDto;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.PromptRepository;
import ai.platform.proma.usecase.chatroom.sidebar.SidebarUpdatePromptEmojiUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SidebarUpdatePromptEmojiService implements SidebarUpdatePromptEmojiUseCase {

    private final PromptRepository promptRepository;
    public ChatRoomIdResponseDto updatePromptEmoji(Long promptId, UpdateEmojiRequestDto updateEmojiRequestDto, User user) {
        Prompt prompt = promptRepository.findByIdAndUser(promptId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));

        prompt.updateEmoji(updateEmojiRequestDto.getEmoji());

        return ChatRoomIdResponseDto.of(prompt.getId());
    }
}
