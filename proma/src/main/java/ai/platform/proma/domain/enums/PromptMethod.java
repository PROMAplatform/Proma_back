package ai.platform.proma.domain.enums;

public enum PromptMethod {
    // ex) Character, Task/Research ..
    CHARACTER("Character"), // Character
    TASK_RESEARCH("Task/Research");

    private final String displayName;

    PromptMethod(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
