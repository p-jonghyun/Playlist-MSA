package playlist.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import playlist.Clients.SongFeignClient;
import playlist.Common.Result.Result;
import playlist.Common.Result.ResultService;

import playlist.Config.UserInfo;
import playlist.Config.WithUser;
import playlist.DTO.PlaylistDto;
import playlist.Entity.Playlist;
import playlist.Entity.PlaylistSong;
import playlist.Service.PlaylistService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/playlists")
@AllArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    private final ResultService resultService;
    private SongFeignClient songFeignClient;

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Result createPlaylist(@WithUser UserInfo userInfo, @RequestBody @Valid PlaylistDto.CreateReq dto) {
        Playlist playlist = playlistService.create(userInfo, dto);
        return resultService.getSuccessResult(new PlaylistDto.createRes(playlist));
    }

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public Result getPlaylists(@WithUser UserInfo userInfo) {

        List<Playlist> playlists = playlistService.getAll(userInfo);
        List<PlaylistDto.Res> playlistDto = new ArrayList<>();

        for(Playlist playlist : playlists) {

            List<PlaylistDto.SongRes> songs = new ArrayList<>();

            for(PlaylistSong playlistSong : playlist.getPlaylistSongs()) {
                Long id = playlistSong.getSongId();
                PlaylistDto.FeignSongRes response = songFeignClient.getSong(id);
                songs.add(response.getData());
            }

            playlistDto.add(new PlaylistDto.Res(playlist, songs));

        }

        return resultService.getSuccessResult(new PlaylistDto.PlaylistRes(playlistDto));
    }

    @PostMapping("/{playlist_id}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Result addToPlaylist(@WithUser UserInfo userInfo,
                                @PathVariable Long playlist_id,
                                @RequestBody @Valid PlaylistDto.addDto dto) {
        return resultService.getSuccessResult(new PlaylistDto.Res(playlistService.addtoPlaylist(dto, userInfo, playlist_id),null));
    }




}
