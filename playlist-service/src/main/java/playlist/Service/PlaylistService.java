package playlist.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import playlist.Config.UserInfo;
import playlist.DTO.PlaylistDto;
import playlist.Entity.Playlist;
import playlist.Entity.PlaylistSong;
import playlist.Repository.PlaylistRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;


    public List<Playlist> getAll(Long userId) {
        return playlistRepository.findByUserId(userId);
    }

    @Transactional
    public Playlist create(Long userId, PlaylistDto.CreateReq dto) {

        return playlistRepository.save(dto.toEntity(userId));
    }

    @Transactional
    public Playlist addtoPlaylist(PlaylistDto.addDto dto, Long userId, Long playlist_id) {

        Playlist playlist = playlistRepository.getOne(playlist_id);

        // null 확인
        if (playlist == null) throw new RuntimeException();

        // 요기는 주인 확
        if (playlist.getUserId() != userId) throw new RuntimeException();

        List<Long> songIds = dto.getSongIds();

        for(Long id : songIds) {
            PlaylistSong playlistSong = dto.toEntity(playlist, id);
            playlist.addPlaylistSong(playlistSong);
        }
        return playlist;
    }
}
