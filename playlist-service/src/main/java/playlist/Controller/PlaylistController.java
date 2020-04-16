package playlist.Controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import playlist.Clients.SongFeignClient;
import playlist.Common.Result.Result;
import playlist.Common.Result.ResultService;

import playlist.DTO.PlaylistDto;
import playlist.Entity.Playlist;
import playlist.Entity.PlaylistSong;
import playlist.Service.PlaylistService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/playlists")
@AllArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    private final ResultService resultService;
    private SongFeignClient songFeignClient;

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Result createPlaylist(HttpServletRequest request, @RequestBody @Valid PlaylistDto.CreateReq dto) {
        Playlist playlist = playlistService.create(getUserId(request), dto);
        return resultService.getSuccessResult(new PlaylistDto.createRes(playlist));
    }

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public Result getPlaylists(HttpServletRequest request) {

        List<Playlist> playlists = playlistService.getAll(getUserId(request));
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
    public Result addToPlaylist(HttpServletRequest request,
                                @PathVariable Long playlist_id,
                                @RequestBody @Valid PlaylistDto.addDto dto) {
        return resultService.getSuccessResult(new PlaylistDto.Res(playlistService.addtoPlaylist(dto, getUserId(request), playlist_id),null));
    }

    private Long getUserId(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        authorizationHeader = authorizationHeader.replace("Bearer ", "");

        Claims claims = Jwts.parser()
                .setSigningKey("jwt_secret_key".getBytes())
                .parseClaimsJws(authorizationHeader).getBody();

        Integer id = (Integer) claims.get("user_id");
        return Long.valueOf(id);
    }


}
