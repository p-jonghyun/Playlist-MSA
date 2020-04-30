package playlist.Service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import playlist.Entity.Song;
import playlist.Exception.SongNotFoundException;
import playlist.Repository.SongRepository;

@Service
@AllArgsConstructor
public class SongService {

    private SongRepository songRepository;

    public Song findSong(Long songId) {

        return songRepository.findById(songId).orElseThrow(SongNotFoundException::new);
    }
}
