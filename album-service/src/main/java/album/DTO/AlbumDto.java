package album.DTO;

import album.Common.Paging.PageDto;
import album.Entity.Album;
import album.Entity.Locale;
import album.Entity.Song;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class AlbumDto {

    @NoArgsConstructor
    @Getter
    public static class createAlbumReq {

        private String title;
        private List<Locale> locales;
        private List<SongDto> songs;

        @Builder
        public createAlbumReq(String title, List<Locale> locales, List<SongDto> songs) {
            this.title = title;
            this.locales = locales;
            this.songs = songs;
        }

        public Album toEntity() {

            List<Song> tmpSongs =
                    this.songs.stream()
                            .map(song -> song.toEntity())
                            .collect(Collectors.toList());

            return Album.builder()
                    .title(title)
                    .locales(locales)
                    .songs(tmpSongs)
                    .build();

        }
    }

    @NoArgsConstructor
    @Getter
    public static class SongDto {
        private String title;
        private Integer track;
        private Integer length;

        @Builder
        public SongDto(String title, Integer track, Integer length) {
            this.title = title;
            this.track = track;
            this.length = length;
        }

        public Song toEntity() {
            return Song.builder()
                    .title(title)
                    .track(track)
                    .length(length)
                    .build();
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class Res {

        private Long id;
        private String title;
        private List<Locale> locales;
        private List<SongRes> songs;

        public Res(final Album album) {
            this.id = album.getId();
            this.title = album.getTitle();
            this.locales = album.getLocales();
            this.songs = album.getSongs().stream()
                    .map(song -> new SongRes(song))
                    .collect(Collectors.toList());
        }

    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class SongRes {

        private Long id;
        private String title;
        private Integer track;
        private Integer length;

        public SongRes(final Song song) {
            this.id = song.getId();
            this.title = song.getTitle();
            this.track = song.getTrack();
            this.length = song.getLength();
        }
    }
    @Getter
    public static class SearchRes {
        private PageDto pages;
        private List<AlbumDto.Res> albums;

        public SearchRes(PageDto pages, List<AlbumDto.Res> albums) {
            this.pages = pages;
            this.albums = albums;
        }
    }
    @Data
    @AllArgsConstructor
    public static class AlbumsRes<T> {
        private T albums;
    }
}
