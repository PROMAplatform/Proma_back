package ai.platform.proma.dto.response;

import ai.platform.proma.domain.Prompt;
import ai.platform.proma.domain.PromptMethods;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class PromptListResponseDto {
    private Long promptId;
    private String promptMethod;
    private String promptTitle;
    private String promptDescription;
    private String promptPreview;
    private String promptCategory;
    private List<SelectBlockDto> listPromptAtom;
    private String emoji;

    @Builder
    public PromptListResponseDto(Prompt prompt, PromptMethods promptMethods, List<SelectBlockDto> listPromptAtom){
        this.promptId = prompt.getId();
        this.promptMethod = promptMethods.getPromptMethod().toString();
        this.promptTitle = prompt.getPromptTitle();
        this.promptDescription = prompt.getPromptDescription();
        this.promptPreview = prompt.getPromptPreview();
        this.emoji = prompt.getEmoji();
        this.promptCategory = prompt.getPromptCategory().toString();
        this.listPromptAtom = listPromptAtom;
    }

    public static PromptListResponseDto of(Prompt prompt, PromptMethods promptMethods, List<SelectBlockDto> selectBlockDtoList){
        return PromptListResponseDto.builder()
                .prompt(prompt)
                .promptMethods(promptMethods)
                .listPromptAtom(selectBlockDtoList)
                .build();
    }


}
