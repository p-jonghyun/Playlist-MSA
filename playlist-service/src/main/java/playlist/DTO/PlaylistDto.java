package playlist.DTO;

import com.sun.tools.javah.Gen;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import playlist.Entity.Genre;
import playlist.Entity.Playlist;
import playlist.Entity.PlaylistSong;
import playlist.Entity.Song;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlaylistDto {



    @Getter
    @NoArgsConstructor
    public static class CreateReq {

        @NotNull
        private String title;

        @Builder
        public CreateReq(@NotNull String title) {
            this.title = title;
        }

        public Playlist toEntity(Long id) {
            return Playlist.builder()
                    .title(this.title)
                    .userId(id)
                    .build();
        }
    }

    @NoArgsConstructor
    @Getter
    public static class createRes {

        private Long playlist_id;

        public createRes(final Playlist playlist) {
            this.playlist_id = playlist.getId();
        }

    }

    @NoArgsConstructor
    @Getter
    public static class Res {

        private Long id;
        private List<SongRes> songs;

        public Res(Playlist playlist, List<SongRes> songs) {
            this.id = playlist.getId();
            this.songs = songs;
        }
    }

    @NoArgsConstructor
    @Getter
    public static class addDto {

        @NotNull
        List<Long> songIds;

        @Builder
        public addDto(@NotNull List<Long> songIds) {
            this.songIds = songIds;
        }

        public PlaylistSong toEntity(Playlist playlist, Song song) {
            return PlaylistSong.builder()
                    .playlist(playlist)
                    .song(song)
                    .build();
        }

    }

    @NoArgsConstructor
    @Getter
    public static class SongRes {

        private Long id;
        private String title;
        private Integer track;
        private Integer length;
        private List<Genre> genres = new ArrayList<>();

        public SongRes(Song song) {
            this.id = song.getId();
            this.title = song.getTitle();
            this.track = song.getTrack();
            this.length = song.getLength();
            this.genres = song.getGenres();
        }
    }

    @Data
    @AllArgsConstructor
    public static class PlaylistRes<T> {
        private T playlists;
    }

    @Data
    public static class RabbitUsers {
        private List<Long> users;
    }
}
