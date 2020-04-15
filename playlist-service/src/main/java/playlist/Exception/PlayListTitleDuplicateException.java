package playlist.Exception;

public class PlayListTitleDuplicateException extends RuntimeException {

    public PlayListTitleDuplicateException() {
        super();
    }

    public PlayListTitleDuplicateException(String message) {
        super(message);
    }

    public PlayListTitleDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
