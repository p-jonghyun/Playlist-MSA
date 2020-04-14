package album.Controller;

import album.Common.Paging.PageDto;
import album.Common.Paging.PageRequest;
import album.Common.Result.Result;
import album.Common.Result.ResultService;
import album.DTO.AlbumDto;
import album.Entity.Album;
import album.Entity.Locale;
import album.Service.AlbumSearchService;
import album.Service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AlbumController {

    private final AlbumService albumService;
    private final AlbumSearchService albumSearchService;
    private final ResultService resultService;

    @PostMapping("/albums")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Result registerAlbum(@RequestBody @Valid AlbumDto.createAlbumReq dto) {
        return resultService.getSuccessResult(new AlbumDto.Res(albumService.create(dto)));
    }

    @GetMapping("/albums")
    public Result getAlbums(PageRequest pageable,
                            @RequestParam(name = "locale") Locale locale) {
        Page<AlbumDto.Res> page =  albumSearchService.getAlbums(locale, pageable.of("title")).map(AlbumDto.Res::new);
        PageDto pages = new PageDto(page, "album");

        return resultService.getSuccessResult(new AlbumDto.SearchRes(pages, page.getContent()));
    }

    @GetMapping("/albums/search")
    @ResponseStatus(value = HttpStatus.OK)
    public Result search(@RequestParam final String title,
                                     @RequestParam final Locale locale) {

        List<Album> albums = albumSearchService.search(title.replaceAll("\n", ""), locale);

        return resultService.getSuccessResult(new AlbumDto.AlbumsRes(albums.stream()
                .map(album -> new AlbumDto.Res(album))
                .collect(Collectors.toList())));
    }


}
