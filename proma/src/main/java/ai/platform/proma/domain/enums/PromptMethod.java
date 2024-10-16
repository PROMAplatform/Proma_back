package ai.platform.proma.domain.enums;

import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum PromptMethod {
    // ex) Character, Task/Research ..
    CHARACTER("Character"), // Character
    FREE("Free"),
    TASK_RESEARCH("Task/Research");

    private final String displayName;

    public static PromptMethod fromValue(String value) {
        return Arrays.stream(PromptMethod.values())
                .filter(v -> v.displayName.equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
