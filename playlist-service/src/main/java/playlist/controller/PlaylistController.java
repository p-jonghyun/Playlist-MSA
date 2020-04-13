package playlist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    @GetMapping("")
    public String getPlaylists() {
        return "여기 나의 플레이리스트들이 있소 가져가시오";
    }
}
