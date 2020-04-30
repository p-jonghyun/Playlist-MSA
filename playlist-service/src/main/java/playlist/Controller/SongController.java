package playlist.Controller;



import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import playlist.Common.Result.Result;
import playlist.Common.Result.ResultService;
import playlist.DTO.AlbumDto;
import playlist.Service.SongService;

@RestController
@AllArgsConstructor
public class SongController {

    private SongService songService;
    private ResultService resultService;

    @GetMapping("/songs/{songId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Result getSong(@PathVariable Long songId) {
        return resultService.getSuccessResult(new AlbumDto.SongRes(songService.findSong(songId)));
    }

}
