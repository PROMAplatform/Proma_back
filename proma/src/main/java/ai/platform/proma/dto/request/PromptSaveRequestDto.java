package ai.platform.proma.dto.request;

import ai.platform.proma.domain.*;
import ai.platform.proma.domain.enums.PromptCategory;
import ai.platform.proma.domain.enums.PromptType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PromptSaveRequestDto {
    private String promptTitle;
    private String promptDescription;
    private String promptPreview;
    private PromptCategory promptCategory;
    private PromptType promptType;
    private List<ListPromptAtom> listPromptAtom;

    public Prompt toEntity(User user, CommunicationMethod communicationMethod, PromptSaveRequestDto promptSaveRequestDto) {
        return Prompt.builder()
                .user(user)
                .promptTitle(promptSaveRequestDto.getPromptTitle())
                .promptDescription(promptSaveRequestDto.getPromptDescription())
                .promptPreview(promptSaveRequestDto.getPromptPreview())
                .communicationMethod(communicationMethod)
                .build();
    }

    public PromptBlock toEntity(Prompt prompt, Block block) {
        return PromptBlock.builder()
                .prompt(prompt)
                .block(block)
                .build();
    }
}
