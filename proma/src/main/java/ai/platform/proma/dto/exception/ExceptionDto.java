package ai.platform.proma.dto.exception;

import ai.platform.proma.exception.ErrorDefine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor
public class ExceptionDto {
    private final String code;
    private final String message;

    public ExceptionDto(ErrorDefine errorDefine) {
        this.code = errorDefine.getErrorCode();
        this.message = errorDefine.getMessage();
    }

    public ExceptionDto(Exception e) {
        this.code = Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value());
        this.message = e.getMessage();
    }
}
