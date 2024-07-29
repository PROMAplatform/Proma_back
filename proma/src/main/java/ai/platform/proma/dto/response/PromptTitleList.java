package ai.platform.proma.dto.response;

import ai.platform.proma.domain.Prompt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PromptTitleList {
    private Long promptId;
    private String promptTitle;

    @Builder
    public PromptTitleList(Prompt prompt) {
        this.promptId = prompt.getId();
        this.promptTitle = prompt.getPromptTitle();
    }

    public static PromptTitleList of(Prompt prompt) {
        return PromptTitleList.builder()
                .prompt(prompt)
                .build();
    }
}
