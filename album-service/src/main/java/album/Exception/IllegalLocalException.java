package album.Exception;

public class IllegalLocalException extends RuntimeException {
    public IllegalLocalException() {
        super();
    }

    public IllegalLocalException(String message) {
        super(message);
    }

    public IllegalLocalException(String message, Throwable cause) {
        super(message, cause);
    }
}
