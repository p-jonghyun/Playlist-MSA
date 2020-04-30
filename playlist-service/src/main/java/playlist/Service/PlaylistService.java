package playlist.Service;

import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import playlist.Config.UserInfo;
import playlist.DTO.PlaylistDto;
import playlist.Entity.Playlist;
import playlist.Entity.PlaylistSong;
import playlist.Entity.Song;
import playlist.Exception.PlayListTitleDuplicateException;
import playlist.Exception.PlaylistMatchException;
import playlist.Exception.PlaylistNotFoundException;
import playlist.Exception.SongNotFoundException;
import playlist.Repository.PlaylistRepository;
import playlist.Repository.SongRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    public List<Playlist> getAll(Long userId) {
        return playlistRepository.findByUserId(userId);
    }

    @Transactional
    public Playlist create(Long userId, PlaylistDto.CreateReq dto) {

        List<Playlist> playlists = getAll(userId);

        for (Playlist playlist : playlists)
            if(playlist.getTitle().equals(dto.getTitle())) throw new PlayListTitleDuplicateException();

        return playlistRepository.save(dto.toEntity(userId));
    }

    @Transactional
    public Playlist addtoPlaylist(PlaylistDto.addDto dto, Long userId, Long playlist_id) {

        Playlist playlist = playlistRepository.getOne(playlist_id);

        // null 확인
        if (playlist == null) throw new PlaylistNotFoundException();

        // 요기는 주인 확
        if (playlist.getUserId() != userId) throw new PlaylistMatchException();

        List<Long> songIds = dto.getSongIds();

        for (Long id : songIds) {
            Song song = songRepository.findById(id).orElseThrow(SongNotFoundException::new);
            PlaylistSong playlistSong = dto.toEntity(playlist, song);
            playlist.addPlaylistSong(playlistSong);
        }

        return playlist;
    }
}
