package playlist.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Playlist {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private Long userId;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private List<PlaylistSong> playlistSongs = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void addPlaylistSong(PlaylistSong playlistSong) {
        playlistSongs.add(playlistSong);
    }

    @Builder
    public Playlist(Long userId, String title) {
        this.title = title;
        this.userId = userId;
    }

    public static Playlist createPlaylist(Long userId, PlaylistSong... playlistSongs) {

        Playlist playlist = new Playlist();
        playlist.setUserId(userId);

        for(PlaylistSong playlistSong : playlistSongs)
            playlist.addPlaylistSong(playlistSong);
        return playlist;
    }
}
