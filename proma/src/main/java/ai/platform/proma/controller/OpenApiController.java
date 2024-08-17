package ai.platform.proma.controller;

import ai.platform.proma.domain.User;
import ai.platform.proma.dto.response.ResponseDto;
import ai.platform.proma.security.LoginUser;
import ai.platform.proma.security.openapi.OpenApiToken;
import ai.platform.proma.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/openapi")
public class OpenApiController {
    private final OpenApiService openApiService;

    @PostMapping("/signup/{promptId}")
    public ResponseDto<OpenApiToken> openApiSignup(
            @LoginUser User user,
            @PathVariable("promptId") Long promptId
            ) {
        return new ResponseDto<>(openApiService.openApiSignup(user, promptId));
    }
}
