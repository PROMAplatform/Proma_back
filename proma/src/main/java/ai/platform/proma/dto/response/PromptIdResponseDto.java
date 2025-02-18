package ai.platform.proma.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PromptIdResponseDto {
    private Long promptId;

    @Builder
    public PromptIdResponseDto(Long promptId) {
        this.promptId = promptId;
    }

    public static PromptIdResponseDto of(Long promptId) {
        return PromptIdResponseDto.builder()
                .promptId(promptId)
                .build();
    }
}
