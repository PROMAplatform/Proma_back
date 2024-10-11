package ai.platform.proma.dto.response;

import ai.platform.proma.domain.Prompt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PromptTitleList {
    private Long promptId;
    private String promptTitle;

    public static PromptTitleList of(Prompt prompt) {
        return PromptTitleList.builder()
                .promptId(prompt.getId())
                .build();
    }
}
