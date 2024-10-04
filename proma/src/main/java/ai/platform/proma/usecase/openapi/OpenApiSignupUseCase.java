package ai.platform.proma.usecase.openapi;

import ai.platform.proma.domain.User;
import ai.platform.proma.security.openapi.OpenApiToken;

public interface OpenApiSignupUseCase {
    OpenApiToken openApiSignup(User user, Long promptId);
}
