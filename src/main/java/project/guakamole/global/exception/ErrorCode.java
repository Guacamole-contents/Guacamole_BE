package project.guakamole.global.exception;

import lombok.Getter;

@Getter
public enum
ErrorCode {
    //global
    INTERNAL_SERVER_ERROR(500, "G001", "Internal Server Error"),
    REQUEST_VALID_FAIL(404, "REQEUST_VALID_FAIL", "Request 검증 실패, 에러 메시지 확인"),
    ENTITY_NOT_FOUND(404, "ENTITY_NOT_FOUND", "해당 데이터가 존재하지 않습니다."),

    //AUTH
    USER_DUPLICATE_EMAIL(404, "USER_DUPLICATE_EMAIL", "중복 이메일 에러"),
    LOGIN_FAIL(401, "LOGIN_FAIL", "로그인 실패"),
    USER_PASSWORD_NOT_CORRECT(401, "USER_PASSWORD_NOT_CORRECT", "비밀번호 불일치"),

    //s3 - image
    FAIL_TO_UPLOAD_IMAGES(503,"FAIL_TO_UPLOAD_IMAGES", "이미지를 업로드 할 수 없습니다."),
    FAIL_TO_DELETE_IMAGE(503, "FAIL_TO_DELETE_IMAGE", "이미지를 삭제할 수 없습니다."),

    //login
    EXPIRED_TOKEN(401, "EXPIRED_TOKEN", "토큰이 만료되었습니다."),
    UNAUTHORIZED_TOKEN(401, "UNAUTHORIZED_TOKEN", "인증되지 않은 토큰");

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(int status, String code, String message) {
        this.code = code;
        this.message = message;
        this.status =status;
    }

}