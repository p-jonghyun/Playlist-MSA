package album.Controller;


import album.Common.Result.Result;
import album.Common.Result.ResultService;
import album.DTO.AlbumDto;
import album.Service.SongService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
