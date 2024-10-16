package ai.platform.proma.domain.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

import java.util.Arrays;
import java.util.Locale;

public enum BlockValue {

    SPEAKER1("상담사","Counselor"),  // 화자
    SPEAKER2("영어 전문가","English expert"),  // 화자
    SPEAKER3("여행 가이드","Tour guide"),  // 화자
    SPEAKER4("의료 전문가","Medical expert"),  // 화자
    SPEAKER5("요리 전문가","Culinary expert"),  // 화자


    LISTENER1("고객 ","Customer"), // 청자
    LISTENER2("학생 ","Student"), // 청자
    LISTENER3("여행자 ","Traveler"), // 청자
    LISTENER4("환자 ","Patient"), // 청자
    LISTENER5("초보 요리사 ","Beginner Cook"), // 청자


    INSTRUCTION1("한국어 ","Korean"),  // 지시
    INSTRUCTION2("Python 코드 ","Python Code"),  // 지시
    INSTRUCTION3("Java 코드 ","Java Code"),  // 지시
    INSTRUCTION4("이미지 분석 ","Image Analysis"),  // 지시
    INSTRUCTION5("단계별 설명 ","Step-by-Step"),  // 지시


    FORM1("표 형식 ","Table Format"),  // 형식
    FORM2("실생활 예시 ","Real-Life Examples"),  // 형식
    FORM3("순서도 ","Flowchart "),  // 형식
    FORM4("단계 형식 ","Steps Format"),  // 형식
    FORM5("다이어그램 ","Diagram"),  // 형식


    REFERENCE1("참고자료","Reference"),  // 참고자료
    REFERENCE2("참고자료","Reference"),  // 참고자료
    REFERENCE3("참고자료","Reference"),  // 참고자료
    REFERENCE4("참고자료","Reference"),  // 참고자료
    REFERENCE5("참고자료","Reference"),  // 참고자료


    REQUIRED1("장단점 ","Pros and Cons"),  // 필수
    REQUIRED2("예상 효과 ","Expected Effects"),  // 필수
    REQUIRED3("해결책 ","Solutions"),  // 필수
    REQUIRED4("긍정적 표현 ","Positive Expressions"),  // 필수
    REQUIRED5("과학적 증거 ","Scientific Evidence"),  // 필수


    EXCLUDED1("속어 제외 ","Slang"),  // 제외
    EXCLUDED2("차별적 언어 제외 ","No Discrimination"),  // 제외
    EXCLUDED3("중복 제외 ","No Repetitions"),  // 제외
    EXCLUDED4("초등학생 수준 ","Elementary-Level"),  // 제외
    EXCLUDED5("정치적 내용 제외 ","No Politics"),  // 제외


    ROLE1("상담사","Counselor"), //역할
    ROLE2("교사","Teacher"), //역할
    ROLE3("과학자","Scientist"), //역할
    ROLE4("개발자","Developer"), //역할
    ROLE5("코미디언","Comedian"), //역할


    CHARACTER1("유머","Humor"), // 성격
    CHARACTER2("분노","Anger"), // 성격
    CHARACTER3("조용함","Quiet"), // 성격
    CHARACTER4("진지함","Serious"), // 성격
    CHARACTER5("두려움","Fear"), // 성격


    BACKGROUND_KNOWLEDGE1("지친 학생","Exhausted Student"), // 배경지식
    BACKGROUND_KNOWLEDGE2("스트레스를 받은 대학생","Stressed College Student"), // 배경지식
    BACKGROUND_KNOWLEDGE3("호기심 많은 연구자","Curious Researcher"), // 배경지식
    BACKGROUND_KNOWLEDGE4("형사","Chief"), // 배경지식
    BACKGROUND_KNOWLEDGE5("에너지 엔지니어","Energy Engineer"), // 배경지식


    RULE1("민감한 보스","Sensitive boss"), // 규칙
    RULE2("정직함","Honesty"), // 규칙
    RULE3("정중한 말투","Respectful Speech"), // 규칙
    RULE4("비공식적 말투","Informal Speech"), // 규칙
    RULE5("목록 형식","List Format"), // 규칙


    FREE1("자유1","Free1"), // 자유형 규격1


    FREE2("자유2","Free2"), // 자유형 규격2


    FREE3("자유3","Free3"), // 자유형 규격3


    FREE4("자유4","Free4"), // 자유형 규격4


    FREE5("자유5","Free5"), // 자유형 규격5


    FREE6("자유6","Free6"), // 자유형 규격6


    FREE7("자유7","Free7"); // 자유형 규격7

    private final String msgKo;
    private final String msgEn;
    private BlockValue(String msgKo, String msgEn) {
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


    public static String fromValue(String value) {
        Locale locale = LocaleContextHolder.getLocale();
        BlockValue blockValue = Arrays.stream(BlockValue.values())
                    .filter(v -> v.msgEn.equalsIgnoreCase(value))
                    .findFirst()
                    .orElse(null);

        if (blockValue == null)
            return value;
        else
            return blockValue.toString();

    }


}
