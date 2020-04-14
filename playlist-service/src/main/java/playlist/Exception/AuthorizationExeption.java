package playlist.Exception;

public class AuthorizationExeption extends RuntimeException {
    public AuthorizationExeption() {
    }

    public AuthorizationExeption(String message) {
        super(message);
    }

    public AuthorizationExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
