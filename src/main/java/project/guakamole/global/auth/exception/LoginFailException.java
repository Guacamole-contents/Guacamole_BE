package project.guakamole.global.auth.exception;

import org.springframework.http.HttpStatus;
import project.guakamole.global.exception.BaseException;
import project.guakamole.global.exception.ErrorCode;

public class LoginFailException extends BaseException {

    private static final ErrorCode code = ErrorCode.LOGIN_FAIL;

    public LoginFailException(String message) {
        super(code, HttpStatus.UNAUTHORIZED, message);
    }
}
