package playlist.Exception;

public class PlaylistNotFoundException extends RuntimeException{
    public PlaylistNotFoundException() {
        super();
    }

    public PlaylistNotFoundException(String message) {
        super(message);
    }

    public PlaylistNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
