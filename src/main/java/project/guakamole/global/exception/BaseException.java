package project.guakamole.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public abstract class BaseException extends RuntimeException {

    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;

    protected BaseException(ErrorCode errorCode, HttpStatus httpStatus, String message) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

}
