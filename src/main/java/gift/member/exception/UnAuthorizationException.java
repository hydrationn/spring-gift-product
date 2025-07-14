package gift.member.exception;

public class UnAuthorizationException extends RuntimeException {
    private String message;

    public UnAuthorizationException(String message) {
        super(message);
    }
}
