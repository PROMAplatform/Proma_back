package ai.platform.proma.domain.enums;

import ai.platform.proma.exception.ApiException;
import ai.platform.proma.exception.ErrorDefine;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;
import java.util.Locale;


public enum BlockCategory {

    // 화자, 청자, 지시, 형식, 참고자료, 필수, 제외, 역할, 성격, 배경지식, 규칙
    SPEAKER("화자","Speaker"),  // 화자
    LISTENER("청자","Listener"), // 청자
    INSTRUCTION("지시","Instruction"),  // 지시
    FORM("형식","Form"),  // 형식
    REFERENCE("참고자료","Reference"),  // 참고자료
    REQUIRED("필수","Required"),  // 필수
    EXCLUDED("제외","Excluded"),  // 제외
    ROLE("역할","Role"), //역할
    CHARACTER("성격","Character"), // 성격
    BACKGROUND_KNOWLEDGE("배경지식","Background"), // 배경지식
    RULE("규칙","Rule"), // 규칙
    FREE("자유","Free"), // 자유형re
    FREE1("자유1","Free1"), // 자유형 규격1
    FREE2("자유2","Free2"), // 자유형 규격2
    FREE3("자유3","Free3"), // 자유형 규격3
    FREE4("자유4","Free4"), // 자유형 규격4
    FREE5("자유5","Free5"), // 자유형 규격5
    FREE6("자유6","Free6"), // 자유형 규격6
    FREE7("자유7","Free7"); // 자유형 규격7

    private final String msgKo;
    private final String msgEn;

    private BlockCategory(String msgKo, String msgEn) {
        this.msgKo = msgKo;
        this.msgEn = msgEn;
    }

    @Override
    public String toString(){
        Locale locale = LocaleContextHolder.getLocale();
        // 한국어: ko, 영어: en, 일본어: ja
        if("en".equals(locale.getLanguage()))
            return this.msgEn;
        else
            return this.msgKo;

    }

    public static BlockCategory fromValue(String value) {
        Locale locale = LocaleContextHolder.getLocale();
        if("en".equals(locale.getLanguage()))
            return Arrays.stream(BlockCategory.values())
                    .filter(v -> v.msgEn.equalsIgnoreCase(value))
                    .findFirst()
                    .orElse(null);
        else
            return Arrays.stream(BlockCategory.values())
                    .filter(v -> v.msgKo.equalsIgnoreCase(value))
                    .findFirst()
                    .orElse(null);
    }
}
