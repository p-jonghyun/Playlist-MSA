package album.Service;

import album.Advice.SongNotFoundException;
import album.Entity.Song;
import album.Repository.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SongService {

    private SongRepository songRepository;

    public Song findSong(Long songId) {

        return songRepository.findById(songId).orElseThrow(SongNotFoundException::new);
    }
}
