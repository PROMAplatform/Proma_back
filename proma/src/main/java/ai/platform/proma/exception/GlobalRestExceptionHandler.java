package ai.platform.proma.exception;

import ai.platform.proma.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestExceptionHandler {
    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<?> handleApiException(ApiException e) {
        return ResponseDto.toResponseEntity(e);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleInvalidArgumentException(MethodArgumentNotValidException e) {
        return ResponseDto.toResponseEntity(e);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<Object> handleJSONConversionException(HttpMessageConversionException e) {
        return ResponseDto.toResponseEntity(e);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseDto.toResponseEntity(e);
    }
}
