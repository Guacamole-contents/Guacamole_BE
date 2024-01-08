package project.guakamole.global.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import project.guakamole.global.exception.BaseException;
import project.guakamole.global.exception.ErrorCode;

@Getter
public class UnauthorizedTokenException extends BaseException {

    private static final ErrorCode code = ErrorCode.UNAUTHORIZED_TOKEN;

    public UnauthorizedTokenException(String message) {
        super(code, HttpStatus.UNAUTHORIZED, message);
    }
}
