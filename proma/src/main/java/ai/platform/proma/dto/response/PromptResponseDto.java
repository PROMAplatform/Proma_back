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
public class PromptResponseDto {
    private final Long id;
    private final String promptTitle;
    private final String promptDescription;
    private final String promptPreview;
    private final PromptCategory promptCategory;
    private final Scrap isScrap;
    private final String userName;
    private final Long userId;
    private final PromptMethods promptMethodsName;

    @Builder
    public PromptResponseDto(Prompt prompt) {
        this.id = prompt.getId();
        this.promptTitle = prompt.getPromptTitle();
        this.promptDescription = prompt.getPromptDescription();
        this.promptPreview = prompt.getPromptPreview();
        this.promptCategory = prompt.getPromptCategory();
        this.isScrap = Scrap.SCRAP;
        this.userName = prompt.getUser().getUserName();
        this.userId = prompt.getUser().getId();
        this.promptMethodsName = prompt.getPromptMethods();
    }
}
