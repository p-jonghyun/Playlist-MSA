package album.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Album {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @ElementCollection(fetch = FetchType.EAGER)
    List<Locale> locales = new ArrayList<>();

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<Song> songs = new ArrayList<>();

    public void setSong(Song song) {
        songs.add(song);
        song.setAlbum(this);
    }

    @Builder
    public Album(String title, List<Locale> locales, List<Song> songs) {
        this.title = title;
        this.locales = locales;
        this.songs = songs;
    }

}
