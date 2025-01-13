package ai.platform.proma.dto.request;

import ai.platform.proma.domain.*;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.domain.enums.PromptMethod;
import ai.platform.proma.domain.enums.Scrap;
import ai.platform.proma.validation.ValidEnum;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public record PromptSaveRequestDto(
        @NotNull(message = "promptTitle must not be null")
        String promptTitle,
        @NotNull(message = "promptDescription must not be null")
        String promptDescription,
        @NotNull(message = "promptPreview must not be null")
        String promptPreview,
        @NotNull(message = "promptCategory must not be null")
        @ValidEnum(enumClass = PromptCategory.class, message = "Invalid  promptCategory")
        String promptCategory,
        @NotNull(message = "promptMethod must not be null")
        @ValidEnum(enumClass = PromptMethod.class, message = "Invalid  promptMethod")
        String promptMethod,
        @NotNull(message = "listPromptAtom must not be null")
        List<ListPromptAtom> listPromptAtom,
        List<AiListPromptAtom> aiListPromptAtom
) {
    public Prompt toEntity(User user, PromptMethods promptMethods) {
        return Prompt.builder()
                .user(user)
                .promptTitle(this.promptTitle)
                .promptDescription(this.promptDescription)
                .promptPreview(this.promptPreview)
                .promptCategory(PromptCategory.fromValue(this.promptCategory))
                .isScrap(Scrap.NOTSCRAP)
                .emoji("💡")
                .promptMethods(promptMethods)
                .build();
    }
}
