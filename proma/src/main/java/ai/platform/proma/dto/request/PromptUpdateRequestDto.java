package ai.platform.proma.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PromptUpdateRequestDto {
    private String promptPreview;
    private List<ListPromptAtom> listPromptAtom;

}
