package ai.platform.proma.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


public record PromptUpdateRequestDto(
        String promptPreview,
        List<ListPromptAtom> listPromptAtom
) {


}
