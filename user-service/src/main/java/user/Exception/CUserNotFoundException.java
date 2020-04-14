package user.Exception;

public class CUserNotFoundException extends RuntimeException {

    public CUserNotFoundException() {
        super();
    }

    public CUserNotFoundException(String message) {
        super(message);
    }

    public CUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
