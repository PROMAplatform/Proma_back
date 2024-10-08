package ai.platform.proma.usecase.openapi;

import ai.platform.proma.domain.User;
import ai.platform.proma.security.openapi.OpenApiToken;

public interface OpenApiSignupUseCase {
    OpenApiToken openApiSignup(Long userId, Long promptId);
}
