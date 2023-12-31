package project.guakamole.global.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import project.guakamole.global.exception.BaseException;
import project.guakamole.global.exception.ErrorCode;

import java.net.http.HttpClient;

@Getter
public class TokenExpiredException extends BaseException {

    private static final ErrorCode code = ErrorCode.EXPIRED_TOKEN;

    public TokenExpiredException(String message) {
        super(code, HttpStatus.UNAUTHORIZED, message);
    }
}
