package project.guakamole.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedException extends BaseException {

    private static final ErrorCode code = ErrorCode.UNAUTHORIZED_TOKEN;

    public UnauthorizedException(String message) {
        super(code, HttpStatus.UNAUTHORIZED, message);
    }
}