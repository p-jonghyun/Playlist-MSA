package album.Advice;

public class SongNotFoundException extends RuntimeException{
    public SongNotFoundException() {
    }

    public SongNotFoundException(String message) {
        super(message);
    }

    public SongNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
