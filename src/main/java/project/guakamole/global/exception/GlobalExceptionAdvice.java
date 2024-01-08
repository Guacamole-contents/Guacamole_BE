package project.guakamole.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.guakamole.domain.user.exception.UserDuplicateEmailException;
import project.guakamole.domain.user.exception.UserPasswordNotCorrectException;
import project.guakamole.global.auth.exception.LoginFailException;
import project.guakamole.global.auth.exception.TokenExpiredException;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    //global
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundExceptionHandler(EntityNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("[exceptionHandle] ex", e);
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    //User
    @ExceptionHandler(UserDuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> userDuplicateEmailExceptionHandler(UserDuplicateEmailException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("[exceptionHandle] ex", e);
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(UserPasswordNotCorrectException.class)
    public ResponseEntity<ErrorResponse> UserPasswordNotCorrectExceptionHandler(UserPasswordNotCorrectException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("[exceptionHandle] ex", e);
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    //인증,인가
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> UnauthorizedExceptionHandler(UnauthorizedException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("[exceptionHandle] ex", e);
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> TokenExpiredExceptionHandler(TokenExpiredException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("[exceptionHandle] ex", e);
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<ErrorResponse> LoginFailExceptionHandler(LoginFailException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("[exceptionHandle] ex", e);
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), e.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodValidExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request){
        log.error("[exceptionHandler] ex", e);
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.REQUEST_VALID_FAIL.getCode()
                , Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
