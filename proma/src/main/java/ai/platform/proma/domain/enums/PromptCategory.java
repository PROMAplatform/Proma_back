package ai.platform.proma.domain.enums;

import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum PromptCategory {
    // ex) IT, 게임, 글쓰기, 건강, 교육, 예술, 기타 ..
    IT("IT"), // IT
    GAME("게임"), // 게임
    WRITING("글쓰기"), // 글쓰기
    HEALTH("건강"), // 건강
    EDUCATION("교육"), // 교육
    ART("예술"), // 예술
    ETC("기타"); // 기타

    private final String displayName;

    public static PromptCategory fromValue(String value) {
        return Arrays.stream(PromptCategory.values())
                .filter(v -> v.displayName.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new ApiException(ErrorDefine.INVALID_PROMPT_CATEGORY));
    }
    @Override
    public String toString() {
        return displayName;
    }
}
