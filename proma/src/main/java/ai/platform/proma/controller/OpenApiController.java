package ai.platform.proma.controller;

import ai.platform.proma.dto.response.ApiPromptListResponseDto;
import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.annotation.LoginUser;
import ai.platform.proma.security.openapi.OpenApiToken;
import ai.platform.proma.usecase.openapi.OpenApiListOpenApiTokenUseCase;
import ai.platform.proma.usecase.openapi.OpenApiSignupUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/openapi")
public class OpenApiController {
    private final OpenApiSignupUseCase openApiSignupUseCase;
    private final OpenApiListOpenApiTokenUseCase openApiListOpenApiTokenUseCase;

    @PostMapping("/signup/{promptId}")
    public ResponseDto<OpenApiToken> openApiSignup(
            @LoginUser Long userId,
            @PathVariable("promptId") Long promptId
            ) {
        return new ResponseDto<>(openApiSignupUseCase.openApiSignup(userId, promptId));
    }

    @GetMapping("/list")
    public ResponseDto<Map<String, List<ApiPromptListResponseDto>>> listOpenApiTokens(
            @LoginUser Long userId
    ) {
        return new ResponseDto<>(openApiListOpenApiTokenUseCase.execute(userId));
    }
}
