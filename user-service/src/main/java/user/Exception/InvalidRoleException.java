package user.Exception;

public class InvalidRoleException extends RuntimeException {
    public InvalidRoleException() {
        super();
    }

    public InvalidRoleException(String message) {
        super(message);
    }

    public InvalidRoleException(String message, Throwable cause) {
        super(message, cause);
    }
}
