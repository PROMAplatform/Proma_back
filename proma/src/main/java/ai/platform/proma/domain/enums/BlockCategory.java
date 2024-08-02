package ai.platform.proma.domain.enums;

import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum BlockCategory {
    // 화자, 청자, 지시, 형식, 참고자료, 필수, 제외, 역할, 성격, 배경지식, 규칙
    SPEAKER("화자"),  // 화자
    LISTENER("청자"), // 청자
    INSTRUCTION("지시"),  // 지시
    FORM("형식"),  // 형식
    REFERENCE("참고자료"),  // 참고자료
    REQUIRED("필수"),  // 필수
    EXCLUDED("제외"),  // 제외
    ROLE("역할"), //역할
    CHARACTER("성격"), // 성격
    BACKGROUND_KNOWLEDGE("배경지식"), // 배경지식
    RULE("규칙"); // 규칙

    private final String displayName;

    public static BlockCategory fromValue(String value) {
        return Arrays.stream(BlockCategory.values())
                .filter(v -> v.displayName.equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return displayName;
    }




}
