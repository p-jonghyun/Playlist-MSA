package playlist.Service;

import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import playlist.Config.UserInfo;
import playlist.Clients.SongFeignClient;
import playlist.DTO.PlaylistDto;
import playlist.Entity.Playlist;
import playlist.Entity.PlaylistSong;
import playlist.Exception.PlayListTitleDuplicateException;
import playlist.Exception.PlaylistMatchException;
import playlist.Exception.PlaylistNotFoundException;
import playlist.Exception.SongNotFoundException;
import playlist.Repository.PlaylistRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongFeignClient feignClient;

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

        try {
            for(Long id : songIds) {
                feignClient.getSong(id);
                PlaylistSong playlistSong = dto.toEntity(playlist, id);
                playlist.addPlaylistSong(playlistSong);
            }
        } catch(Exception e) {
            throw new SongNotFoundException();
        }

        return playlist;
    }
}
