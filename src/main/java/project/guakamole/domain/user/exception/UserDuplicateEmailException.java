package project.guakamole.domain.user.exception;

import org.springframework.http.HttpStatus;
import project.guakamole.global.exception.BaseException;
import project.guakamole.global.exception.ErrorCode;

public class UserDuplicateEmailException extends BaseException {
    private static final ErrorCode code = ErrorCode.USER_DUPLICATE_EMAIL;

    public UserDuplicateEmailException(String message) {
        super(code, HttpStatus.BAD_REQUEST, message);
    }
}
