package playlist.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(
        uniqueConstraints={
                @UniqueConstraint(
                        columnNames={"playlist_id","song_id"}
                )
        }
)
public class PlaylistSong {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @Column(name = "song_id")
    private Long songId;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @Builder
    public PlaylistSong(Playlist playlist, Long songId) {
        this.songId = songId;
        this.playlist = playlist;
    }
}
