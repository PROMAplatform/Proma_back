package ai.platform.proma.dto.exception;

import ai.platform.proma.exception.ErrorDefine;
import lombok.Getter;
import org.springframework.http.converter.HttpMessageConversionException;

import java.util.Optional;

@Getter
public class JSONConvertExceptionDto extends ExceptionDto {
    private final String message;
    private final String cause;

    public JSONConvertExceptionDto(HttpMessageConversionException jsonException) {
        super(ErrorDefine.INVALID_ARGUMENT);

        this.message = jsonException.getMessage();
        this.cause = Optional.ofNullable(jsonException.getCause())
                .map(Throwable::toString)
                .orElse("Non-Throwable Cause");
    }
}
