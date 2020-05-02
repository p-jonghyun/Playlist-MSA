package playlist.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import playlist.Entity.Album;

@Getter
public class AlbumCreatedEvent {

    private Album album;

    public AlbumCreatedEvent(Album album) {
        this.album = album;
    }
}
