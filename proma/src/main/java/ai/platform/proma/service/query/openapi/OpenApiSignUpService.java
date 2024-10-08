package ai.platform.proma.service.query.openapi;

import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.User;
import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import ai.platform.proma.repository.PromptRepository;
import ai.platform.proma.repository.UserRepository;
import ai.platform.proma.security.openapi.OpenApiToken;
import ai.platform.proma.security.openapi.OpenApiTokenProvider;
import ai.platform.proma.usecase.openapi.OpenApiSignupUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OpenApiSignUpService implements OpenApiSignupUseCase {

    private final PromptRepository promptRepository;
    private final UserRepository userRepository;
    public OpenApiToken openApiSignup(Long userId, Long promptId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Prompt prompt = promptRepository.findByIdAndUser(promptId, user)
                .orElseThrow(() -> new ApiException(ErrorDefine.PROMPT_NOT_FOUND));
        OpenApiTokenProvider openApiTokenProvider = new OpenApiTokenProvider();
        return openApiTokenProvider.createTotalToken(user.getSocialId(), user.getRole(), prompt.getId());
    }
}
