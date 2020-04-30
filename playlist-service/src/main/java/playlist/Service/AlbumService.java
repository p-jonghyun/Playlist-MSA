package playlist.Service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import playlist.DTO.AlbumDto;
import playlist.Entity.Album;
import playlist.Repository.AlbumRepository;
import playlist.Repository.SongRepository;

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



