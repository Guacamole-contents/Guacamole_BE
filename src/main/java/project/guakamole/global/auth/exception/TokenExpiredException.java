package project.guakamole.global.auth.exception;

import lombok.Getter;
import project.guakamole.global.exception.ErrorCode;

@Getter
public class TokenExpiredException extends RuntimeException {

    private final ErrorCode errorCode;

    public TokenExpiredException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
