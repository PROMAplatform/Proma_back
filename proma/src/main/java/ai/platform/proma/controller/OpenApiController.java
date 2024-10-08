package ai.platform.proma.controller;

import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.annotation.LoginUser;
import ai.platform.proma.security.openapi.OpenApiToken;
import ai.platform.proma.usecase.openapi.OpenApiSignupUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/openapi")
public class OpenApiController {
    private final OpenApiSignupUseCase openApiSignupUseCase;

    @PostMapping("/signup/{promptId}")
    public ResponseDto<OpenApiToken> openApiSignup(
            @LoginUser Long userId,
            @PathVariable("promptId") Long promptId
            ) {
        return new ResponseDto<>(openApiSignupUseCase.openApiSignup(userId, promptId));
    }
}
