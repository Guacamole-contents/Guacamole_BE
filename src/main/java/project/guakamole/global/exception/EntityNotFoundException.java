package project.guakamole.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotFoundException extends BaseException {

    private static final ErrorCode code = ErrorCode.ENTITY_NOT_FOUND;

    public EntityNotFoundException(String message) {
        super(code, HttpStatus.UNAUTHORIZED, message);
    }
}