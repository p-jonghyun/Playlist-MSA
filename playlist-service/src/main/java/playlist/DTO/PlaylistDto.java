package playlist.DTO;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import playlist.Clients.SongFeignClient;
import playlist.Entity.Playlist;
import playlist.Entity.PlaylistSong;

import javax.validation.constraints.NotNull;
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

        public PlaylistSong toEntity(Playlist playlist, Long id) {
            return PlaylistSong.builder()
                    .playlist(playlist)
                    .songId(id)
                    .build();
        }

    }

    @Data
    @AllArgsConstructor
    public static class FeignSongRes {
        private SongRes data;
        private boolean success;
        private String msg;
        private List<String> errors;
    }

    @NoArgsConstructor
    @Getter
    public static class SongRes {

        private Long id;
        private String title;
        private Integer track;
        private Integer length;

    }

    @Data
    @AllArgsConstructor
    public static class PlaylistRes<T> {
        private T playlists;
    }

}
