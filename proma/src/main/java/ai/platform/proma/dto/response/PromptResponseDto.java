package ai.platform.proma.dto.response;

import ai.platform.proma.domain.PromptMethods;
import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.domain.enums.Scrap;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class PromptResponseDto {
    private final Long id;
    private final String promptTitle;
    private final String promptDescription;
    private final String promptPreview;
    private final PromptCategory promptCategory;
    private final Scrap isScrap;
    private final String userName;
    private final Long userId;
    private final PromptMethods promptMethods;
    public PromptResponseDto of(Prompt prompt){
        return PromptResponseDto.builder()
                .id(prompt.getId())
                .promptTitle(promptTitle)
                .promptDescription(promptDescription)
                .promptPreview(promptPreview)
                .promptCategory(promptCategory)
                .isScrap(isScrap)
                .userName(userName)
                .userId(userId)
                .promptMethods(promptMethods)
                .build();
    }
}
