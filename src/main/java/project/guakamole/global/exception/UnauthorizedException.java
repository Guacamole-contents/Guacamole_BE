package project.guakamole.global.exception;

public class UnauthorizedException extends RuntimeException {
    private final String errorMessage;

    public UnauthorizedException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
