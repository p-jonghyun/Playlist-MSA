package playlist.event;

import lombok.Data;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import playlist.Entity.Song;

@Getter
public class SongCreatedEvent {

    private Song song;

    public SongCreatedEvent(Song song) {
        this.song = song;
    }
}
