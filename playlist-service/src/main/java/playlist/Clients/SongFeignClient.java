package playlist.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import playlist.Controller.PlaylistController;
import playlist.DTO.PlaylistDto;

@FeignClient(name = "messages", url = "localhost:8090/songs")
public interface SongFeignClient {

    @GetMapping("/{songId}")
    PlaylistDto.FeignSongRes getSong(@PathVariable("songId") Long songId);

}
