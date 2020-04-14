package album.Service;

import album.DTO.AlbumDto;
import album.Entity.Album;
import album.Entity.Song;
import album.Repository.AlbumRepository;
import album.Repository.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    public Album create(AlbumDto.createAlbumReq dto) {

        Album album = dto.toEntity();

        album.getSongs().stream()
                .forEach(song -> song.setAlbum(album));

        return albumRepository.save(album);
    }
}



