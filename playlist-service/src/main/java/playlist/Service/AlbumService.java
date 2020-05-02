package playlist.Service;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playlist.DTO.AlbumDto;
import playlist.Entity.Album;
import playlist.Repository.AlbumRepository;
import playlist.Repository.SongRepository;
import playlist.event.AlbumCreatedEvent;
import playlist.event.SongCreatedEvent;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ApplicationEventPublisher eventPublisher;
    private ObjectMapper objectMapper;

    public Album create(AlbumDto.createAlbumReq dto) {

        Album album = dto.toEntity();

        album.getSongs().stream()
                .forEach(
                        song -> {
                            song.setAlbum(album);
                            eventPublisher.publishEvent(new SongCreatedEvent(song));
                        }
                );
        eventPublisher.publishEvent(new AlbumCreatedEvent(album));
        return albumRepository.save(album);
    }


}



