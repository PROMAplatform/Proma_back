package ai.platform.proma.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

@NoArgsConstructor
public class ApiPromptListResponseDto {
    private Long promptId;
    private String promptTitle;
    private String apiToken;
    private String secretKey;

    @Builder
    public ApiPromptListResponseDto(Long promptId, String promptTitle, String apiToken, String secretKey) {
        this.promptId = promptId;
        this.promptTitle = promptTitle;
        this.apiToken = apiToken;
        this.secretKey = secretKey;
    }

    public static ApiPromptListResponseDto of(Long promptId, String promptTitle, String apiToken, String secretKey) {
        return ApiPromptListResponseDto.builder()
                .promptId(promptId)
                .promptTitle(promptTitle)
                .apiToken(apiToken)
                .secretKey(secretKey)
                .build();
    }
}
