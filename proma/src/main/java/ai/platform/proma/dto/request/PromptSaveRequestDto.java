package ai.platform.proma.dto.request;

import ai.platform.proma.domain.*;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.domain.enums.PromptMethod;
import ai.platform.proma.domain.enums.Scrap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


public record PromptSaveRequestDto(
        String promptTitle,
        String promptDescription,
        String promptPreview,
        String promptCategory,
        String promptMethod,
        List<ListPromptAtom> listPromptAtom
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
