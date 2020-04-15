package playlist.Exception;

public class PlaylistMatchException extends RuntimeException {
    public PlaylistMatchException() {
        super();
    }

    public PlaylistMatchException(String message) {
        super(message);
    }

    public PlaylistMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
