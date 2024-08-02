package ai.platform.proma.dto.request;

import ai.platform.proma.domain.*;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.domain.enums.PromptMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PromptSaveRequestDto {
    private String promptTitle;
    private String promptDescription;
    private String promptPreview;
    private String promptCategory;
    private String promptMethod;
    private List<ListPromptAtom> listPromptAtom;

    public Prompt toEntity(User user, PromptMethods promptMethods, PromptSaveRequestDto promptSaveRequestDto) {
        return Prompt.builder()
                .user(user)
                .promptTitle(promptSaveRequestDto.getPromptTitle())
                .promptDescription(promptSaveRequestDto.getPromptDescription())
                .promptPreview(promptSaveRequestDto.getPromptPreview())
                .promptCategory(PromptCategory.fromValue(promptSaveRequestDto.getPromptCategory()))
                .promptMethods(promptMethods)
                .build();
    }
}
