package album.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Song {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private Integer track;

    private Integer length;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="album_id")
    private Album album;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Builder
    public Song(String title, Integer track, Integer length, Album album) {
        this.title = title;
        this.track = track;
        this.length = length;
        this.album = album;
    }
}
