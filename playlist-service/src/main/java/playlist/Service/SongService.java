package playlist.Service;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playlist.Entity.Song;
import playlist.Exception.SongNotFoundException;
import playlist.Repository.SongRepository;

@Service
@RequiredArgsConstructor
public class SongService {

    private SongRepository songRepository;

    public Song findSong(Long songId) {

        return songRepository.findById(songId).orElseThrow(SongNotFoundException::new);
    }
}
