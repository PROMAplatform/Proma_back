package ai.platform.proma.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorDefine {
    // Bad Request
    INVALID_ARGUMENT("4000", HttpStatus.BAD_REQUEST, "Bad Request: Invalid Arguments"),

    // Not Found
    USER_NOT_FOUND("4040", HttpStatus.NOT_FOUND, "Not Found: User Not Found"),
    COMMUNICATION_METHOD_NOT_FOUND("4041", HttpStatus.NOT_FOUND, "Not Found: Communication Method Not Found"),
    POST_NOT_FOUND("4042", HttpStatus.NOT_FOUND, "Not Found: Post Not Found"),

    //Forbidden
    UNAUTHORIZED_USER("4030", HttpStatus.FORBIDDEN, "Forbidden: Unauthorized User");


    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
