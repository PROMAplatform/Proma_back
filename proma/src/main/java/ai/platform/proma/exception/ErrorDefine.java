package ai.platform.proma.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorDefine {
    // Bad Request
    INVALID_ARGUMENT("4000", HttpStatus.BAD_REQUEST, "Bad Request: Invalid Arguments"),
    INVALID_PROMPT_CATEGORY("4001", HttpStatus.BAD_REQUEST, "Bad Request: Invalid PromptCategory"),
    INVALID_PROMPT_METHOD("4002", HttpStatus.BAD_REQUEST, "Bad Request: Invalid PromptMethod"),
    INVALID_BLOCK_CATEGORY("4003", HttpStatus.BAD_REQUEST, "Bad Request: Invalid BlockCategory"),
    INVALID_BLOCK_METHOD("4004", HttpStatus.BAD_REQUEST, "Bad Request: Invalid BlockMethod"),
    // Not Found
    USER_NOT_FOUND("4040", HttpStatus.NOT_FOUND, "Not Found: User Not Found"),
    COMMUNICATION_METHOD_NOT_FOUND("4041", HttpStatus.NOT_FOUND, "Not Found: Communication Method Not Found"),
    POST_NOT_FOUND("4042", HttpStatus.NOT_FOUND, "Not Found: Post Not Found"),
    PROMPT_NOT_FOUND("4043", HttpStatus.NOT_FOUND, "Not Found: Prompt Not Found"),
    CHAT_ROOM_NOT_FOUND("4044", HttpStatus.NOT_FOUND, "Not Found: Chat Room Not Found"),
    BLOCK_NOT_FOUND("4045", HttpStatus.NOT_FOUND, "Not Found: Block Not Found"),
    MESSAGE_NOT_FOUND("4046", HttpStatus.NOT_FOUND, "Not Found: Message Not Found"),
    PROMPT_BLOCK_NOT_FOUND("4047", HttpStatus.NOT_FOUND, "Not Found: Prompt Block Not Found"),

    //Forbidden
    UNAUTHORIZED_USER("4030", HttpStatus.FORBIDDEN, "Forbidden: Unauthorized User");


    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;
}
