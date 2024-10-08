package ai.platform.proma.service.command.chatroom.sidebar;

import ai.platform.proma.domain.Message;
import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.User;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.MessageRepository;
import ai.platform.proma.repository.PromptRepository;
import ai.platform.proma.repository.UserRepository;
import ai.platform.proma.usecase.chatroom.sidebar.SidebarDeletePromptUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SidebarDeletePromptService implements SidebarDeletePromptUseCase {

    private final PromptRepository promptRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public Boolean deletePrompt(Long promptId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Prompt prompt = promptRepository.findByIdAndUser(promptId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));
        List<Message> messages = messageRepository.findAllByPromptId(promptId);

        messages.forEach(Message::update);

        promptRepository.delete(prompt);

        return true;
    }
}
